package com.migracion.rangel.application.encounter;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.encounter.command.*;
import com.migracion.rangel.application.encounter.usecase.*;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.domain.encounter.model.aggregate.Encounter;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import com.migracion.rangel.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.migracion.rangel.domain.encountertype.model.aggregate.EncounterType;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.domain.encountertype.port.repository.EncounterTypeRepository;
import com.migracion.rangel.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.migracion.rangel.domain.encountermodality.model.aggregate.EncounterModality;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;
import com.migracion.rangel.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.migracion.rangel.domain.encounterstatus.model.aggregate.EncounterStatus;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
class EncounterUseCasesTest {
    @Test void crudCanChangeAllEditableReferencesAndPreservesCreatorAndCreationDate() {
        var f = new Fixture(); var original = f.register.execute(f.command(0)); var id = new EncounterId(original.id());
        var get = new GetEncounterByIdUseCase(f.repository);
        assertEquals(original, get.execute(id)); assertEquals(List.of(original), new ListEncounterUseCase(f.repository).execute());
        var changed = f.update.execute(new UpdateEncounterCommand(id, f.otherRecord.id(), f.otherProfessional.id(), f.otherType.id(),
                f.started.plusDays(1), f.started.plusDays(1).plusHours(2), "Nuevo", "Nueva", f.otherModality.id(), f.otherStatus.id(), f.otherProfessional.id()));
        assertEquals(original.createdAt(), changed.createdAt()); assertEquals(original.createdBy(), changed.createdBy());
        assertEquals(f.otherRecord.id().value(), changed.clinicalRecordId()); assertEquals(f.otherProfessional.id().value(), changed.professionalId());
        assertEquals(f.otherType.id().value(), changed.encounterTypeId()); assertEquals(f.otherModality.id().value(), changed.modalityId());
        assertEquals(f.otherStatus.id().value(), changed.statusId()); assertEquals(f.otherProfessional.id().value(), changed.updatedBy());
        var delete = new DeleteEncounterUseCase(f.repository); assertEquals(id, delete.execute(id).id());
        assertTrue(f.repository.findAll().isEmpty());
        assertThrows(EncounterNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(EncounterNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(EncounterNotFoundApplicationException.class, () -> f.update.execute(f.updateCommand(id, 0)));
    }
    @Test void everyMissingReferencePreventsRegistrationAndPartialUpdate() {
        var exceptions = List.of(ClinicalRecordNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class,
                EncounterTypeNotFoundApplicationException.class, EncounterModalityNotFoundApplicationException.class,
                EncounterStatusNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class,
                ProfessionalNotFoundApplicationException.class);
        for (int missing = 1; missing <= 7; missing++) {
            var f = new Fixture(); var original = f.register.execute(f.command(0)); var id = new EncounterId(original.id());
            var registration = f.command(missing);
            assertThrows(exceptions.get(missing - 1), () -> f.register.execute(registration));
            if (missing != 6) {
                var update = f.updateCommand(id, missing);
                assertThrows(exceptions.get(missing - 1), () -> f.update.execute(update));
            }
            assertEquals(original, new GetEncounterByIdUseCase(f.repository).execute(id)); assertEquals(1, f.repository.saves);
        }
    }
    @Test void repeatedEncountersAreAllowedAndInactiveCatalogsDoNotInventBusinessRules() {
        var f = new Fixture(); var first = f.register.execute(f.command(0)); var second = f.register.execute(f.command(0));
        assertNotEquals(first.id(), second.id()); assertEquals(first.clinicalRecordId(), second.clinicalRecordId());
        assertFalse(f.type.active()); assertFalse(f.modality.active()); assertFalse(f.status.active()); assertEquals(2, f.repository.findAll().size());
    }
    @Test void missingEndDateOrUpdaterCannotSaveOrModifyEncounter() {
        var f = new Fixture(); var original = f.register.execute(f.command(0)); var id = new EncounterId(original.id());
        assertThrows(NullPointerException.class, () -> f.register.execute(new RegisterEncounterCommand(f.record.id(), f.professional.id(), f.type.id(),
                f.started, null, "Nuevo", "Nueva", f.modality.id(), f.status.id(), f.professional.id(), f.professional.id())));
        assertThrows(NullPointerException.class, () -> f.register.execute(new RegisterEncounterCommand(f.record.id(), f.professional.id(), f.type.id(),
                f.started, f.started, "Nuevo", "Nueva", f.modality.id(), f.status.id(), f.professional.id(), null)));
        assertThrows(NullPointerException.class, () -> f.update.execute(new UpdateEncounterCommand(id, f.record.id(), f.professional.id(), f.type.id(),
                f.started, null, "Nuevo", "Nueva", f.modality.id(), f.status.id(), f.professional.id())));
        assertThrows(NullPointerException.class, () -> f.update.execute(new UpdateEncounterCommand(id, f.record.id(), f.professional.id(), f.type.id(),
                f.started, f.started, "Nuevo", "Nueva", f.modality.id(), f.status.id(), null)));
        assertEquals(original, new GetEncounterByIdUseCase(f.repository).execute(id)); assertEquals(1, f.repository.saves);
    }
    private static <T> T parent(Class<T> type, Map<?, ?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) return Optional.ofNullable(values.get(args[0]));
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static Professional professional(String document) {
        return Professional.register(DocumentTypeId.generate(), document, "Nombre", "Apellido", ProfessionalTypeId.generate(), "LIC1", true, CityMunicipalityId.generate());
    }
    private static ClinicalRecord record(String number, ProfessionalId creator) {
        var opened = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
        return ClinicalRecord.register(PatientId.generate(), LocalDateTime.parse("2026-10-03T08:00:00"), number, opened, opened.plusHours(1), ClinicalRecordStatusId.generate(), creator);
    }
    private static class Fixture {
        final Professional professional = professional("PRO1"), otherProfessional = professional("PRO2");
        final ClinicalRecord record = record("HC1", professional.id()), otherRecord = record("HC2", professional.id());
        final EncounterType type = EncounterType.register("TYPE1", "Tipo 1", false), otherType = EncounterType.register("TYPE2", "Tipo 2", true);
        final EncounterModality modality = EncounterModality.register("MOD1", "Modalidad 1", false), otherModality = EncounterModality.register("MOD2", "Modalidad 2", true);
        final EncounterStatus status = EncounterStatus.register("STAT1", "Estado 1", false), otherStatus = EncounterStatus.register("STAT2", "Estado 2", true);
        final OffsetDateTime started = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
        final ClinicalRecordRepository records = parent(ClinicalRecordRepository.class, Map.of(record.id(), record, otherRecord.id(), otherRecord));
        final ProfessionalRepository professionals = parent(ProfessionalRepository.class, Map.of(professional.id(), professional, otherProfessional.id(), otherProfessional));
        final EncounterTypeRepository types = parent(EncounterTypeRepository.class, Map.of(type.id(), type, otherType.id(), otherType));
        final EncounterModalityRepository modalities = parent(EncounterModalityRepository.class, Map.of(modality.id(), modality, otherModality.id(), otherModality));
        final EncounterStatusRepository statuses = parent(EncounterStatusRepository.class, Map.of(status.id(), status, otherStatus.id(), otherStatus));
        final Repository repository = new Repository();
        final RegisterEncounterUseCase register = new RegisterEncounterUseCase(repository, records, professionals, types, modalities, statuses);
        final UpdateEncounterUseCase update = new UpdateEncounterUseCase(repository, records, professionals, types, modalities, statuses);
        RegisterEncounterCommand command(int missing) {
            return new RegisterEncounterCommand(missing == 1 ? ClinicalRecordId.generate() : record.id(), missing == 2 ? ProfessionalId.generate() : professional.id(),
                    missing == 3 ? EncounterTypeId.generate() : type.id(), started, started.plusHours(1), "Motivo", "Condición",
                    missing == 4 ? EncounterModalityId.generate() : modality.id(), missing == 5 ? EncounterStatusId.generate() : status.id(),
                    missing == 6 ? ProfessionalId.generate() : professional.id(), missing == 7 ? ProfessionalId.generate() : professional.id());
        }
        UpdateEncounterCommand updateCommand(EncounterId id, int missing) {
            var c = command(missing);
            return new UpdateEncounterCommand(id, c.clinicalRecordId(), c.professionalId(), c.encounterTypeId(), c.startedAt(), c.endedAt(), "Nuevo", "Nueva",
                    c.modalityId(), c.statusId(), c.updatedBy());
        }
    }
    private static class Repository implements EncounterRepository {
        final Map<EncounterId, Encounter> values = new LinkedHashMap<>(); int saves;
        public Encounter save(Encounter item) { saves++; values.put(item.id(), item); return item; }
        public Optional<Encounter> findById(EncounterId id) { return Optional.ofNullable(values.get(id)); }
        public List<Encounter> findAll() { return List.copyOf(values.values()); }
        public void delete(Encounter item) { values.remove(item.id()); }
    }
}
