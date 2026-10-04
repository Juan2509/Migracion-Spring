package com.migracion.rangel.application.treatmentplan;
import java.util.*;
import java.time.OffsetDateTime;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentplan.model.aggregate.TreatmentPlan;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.application.treatmentplan.command.*;
import com.migracion.rangel.application.treatmentplan.usecase.*;
import com.migracion.rangel.application.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import com.migracion.rangel.domain.treatmentstatus.model.aggregate.TreatmentStatus;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.migracion.rangel.domain.encounter.model.aggregate.Encounter;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
class TreatmentPlanUseCasesTest {
    @Test void crudChangesAllDetailsAndReferencesPreservingCreation() {
        var f = new Fixture(); var original = f.register.execute(f.command(0)); var id = new TreatmentPlanId(original.id()); var get = new GetTreatmentPlanByIdUseCase(f.repository);
        assertEquals(original, get.execute(id)); assertEquals(List.of(original), new ListTreatmentPlanUseCase(f.repository).execute());
        var changed = f.update.execute(new UpdateTreatmentPlanCommand(id, f.otherEncounter.id(), "Otro", "Otra", f.start.plusDays(2), f.start.plusDays(3), f.otherStatus.id(), f.otherProfessional.id()));
        assertEquals(original.createdAt(), changed.createdAt()); assertEquals(f.otherEncounter.id().value(), changed.encounterId());
        assertEquals(f.otherProfessional.id().value(), changed.professionalId()); assertEquals(f.otherStatus.id().value(), changed.treatmentStatusId());
        assertEquals("Otro", changed.title()); assertEquals("Otra", changed.description()); assertEquals(f.start.plusDays(2), changed.startDate()); assertEquals(f.start.plusDays(3), changed.endDate());
        var delete = new DeleteTreatmentPlanUseCase(f.repository); assertEquals(id, delete.execute(id).id()); assertTrue(f.repository.findAll().isEmpty());
        assertThrows(TreatmentPlanNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(TreatmentPlanNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(TreatmentPlanNotFoundApplicationException.class, () -> f.update.execute(f.updateCommand(id, 0)));
    }
    @Test void everyMissingParentPreventsRegistrationAndPartialUpdate() {
        var exceptions = List.of(EncounterNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class, TreatmentStatusNotFoundApplicationException.class);
        for (int missing = 1; missing <= 3; missing++) {
            var f = new Fixture(); var original = f.register.execute(f.command(0)); var id = new TreatmentPlanId(original.id());
            var command = f.command(missing); var update = f.updateCommand(id, missing);
            assertThrows(exceptions.get(missing - 1), () -> f.register.execute(command));
            assertThrows(exceptions.get(missing - 1), () -> f.update.execute(update));
            assertEquals(original, new GetTreatmentPlanByIdUseCase(f.repository).execute(id)); assertEquals(1, f.repository.saves);
        }
    }
    @Test void repeatedPlansAndInactiveStatusAreAllowedButEndDateAndTitleLimitAreRequired() {
        var f = new Fixture(); var first = f.register.execute(f.command(0)); var second = f.register.execute(f.command(0));
        assertNotEquals(first.id(), second.id()); assertFalse(f.status.active()); var id = new TreatmentPlanId(first.id());
        assertThrows(NullPointerException.class, () -> f.register.execute(new RegisterTreatmentPlanCommand(f.encounter.id(), "Título", "Descripción", f.start, null, f.status.id(), f.professional.id())));
        assertThrows(IllegalArgumentException.class, () -> f.update.execute(new UpdateTreatmentPlanCommand(id, f.otherEncounter.id(), "x".repeat(201), "Otra", f.start, f.start, f.status.id(), f.professional.id())));
        assertEquals(first, new GetTreatmentPlanByIdUseCase(f.repository).execute(id)); assertEquals(2, f.repository.saves);
    }
    private static <T> T parent(Class<T> type, Map<?, ?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) return Optional.ofNullable(values.get(args[0]));
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static Professional professional(String doc) { return Professional.register(DocumentTypeId.generate(), doc, "Nombre", "Apellido", ProfessionalTypeId.generate(), "LIC1", true, CityMunicipalityId.generate()); }
    private static Encounter encounter(ProfessionalId professional) {
        var started = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
        return Encounter.register(ClinicalRecordId.generate(), professional, EncounterTypeId.generate(), started, started.plusHours(1), "Motivo", "Condición", EncounterModalityId.generate(), EncounterStatusId.generate(), professional, professional);
    }
    private static class Fixture {
        final Professional professional = professional("PRO1"), otherProfessional = professional("PRO2");
        final Encounter encounter = encounter(professional.id()), otherEncounter = encounter(otherProfessional.id());
        final TreatmentStatus status = TreatmentStatus.register("ONE", "Uno", false), otherStatus = TreatmentStatus.register("TWO", "Dos", true);
        final LocalDate start = LocalDate.of(2026, 10, 3);
        final EncounterRepository encounters = parent(EncounterRepository.class, Map.of(encounter.id(), encounter, otherEncounter.id(), otherEncounter));
        final ProfessionalRepository professionals = parent(ProfessionalRepository.class, Map.of(professional.id(), professional, otherProfessional.id(), otherProfessional));
        final TreatmentStatusRepository statuses = parent(TreatmentStatusRepository.class, Map.of(status.id(), status, otherStatus.id(), otherStatus));
        final Repository repository = new Repository();
        final RegisterTreatmentPlanUseCase register = new RegisterTreatmentPlanUseCase(repository, encounters, professionals, statuses);
        final UpdateTreatmentPlanUseCase update = new UpdateTreatmentPlanUseCase(repository, encounters, professionals, statuses);
        RegisterTreatmentPlanCommand command(int missing) { return new RegisterTreatmentPlanCommand(missing == 1 ? EncounterId.generate() : encounter.id(), "Título", "Descripción", start, start.plusDays(1), missing == 3 ? TreatmentStatusId.generate() : status.id(), missing == 2 ? ProfessionalId.generate() : professional.id()); }
        UpdateTreatmentPlanCommand updateCommand(TreatmentPlanId id, int missing) {
            var c = command(missing); return new UpdateTreatmentPlanCommand(id, c.encounterId(), "Otro", "Otra", c.startDate(), c.endDate(), c.treatmentStatusId(), c.professionalId());
        }
    }
    private static class Repository implements TreatmentPlanRepository {
        final Map<TreatmentPlanId, TreatmentPlan> values = new LinkedHashMap<>(); int saves;
        public TreatmentPlan save(TreatmentPlan item) { saves++; values.put(item.id(), item); return item; }
        public Optional<TreatmentPlan> findById(TreatmentPlanId id) { return Optional.ofNullable(values.get(id)); }
        public List<TreatmentPlan> findAll() { return List.copyOf(values.values()); }
        public void delete(TreatmentPlan item) { values.remove(item.id()); }
    }
}
