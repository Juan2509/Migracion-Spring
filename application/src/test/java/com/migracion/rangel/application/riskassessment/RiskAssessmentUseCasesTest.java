package com.migracion.rangel.application.riskassessment;
import java.util.*;
import java.time.OffsetDateTime;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.riskassessment.command.*;
import com.migracion.rangel.application.riskassessment.usecase.*;
import com.migracion.rangel.application.riskassessment.exception.RiskAssessmentNotFoundApplicationException;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.riskassessment.model.aggregate.RiskAssessment;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
import com.migracion.rangel.domain.riskassessment.port.repository.RiskAssessmentRepository;
import com.migracion.rangel.domain.encounter.model.aggregate.Encounter;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.domain.risklevel.model.aggregate.RiskLevel;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
class RiskAssessmentUseCasesTest {
    @Test void crudChangesReferencesIndicatorsTextsAndAssessmentDate() {
        var f = new Fixture(); var original = f.register.execute(f.command(0)); var id = new RiskAssessmentId(original.id());
        var get = new GetRiskAssessmentByIdUseCase(f.repository);
        assertEquals(original, get.execute(id)); assertEquals(List.of(original), new ListRiskAssessmentUseCase(f.repository).execute());
        var changed = f.update.execute(new UpdateRiskAssessmentCommand(id, f.otherEncounter.id(), f.otherLevel.id(), true, true, true, true, true,
                "R2", "P2", "A2", "O2", f.time.plusDays(1), f.otherProfessional.id()));
        assertEquals(original.id(), changed.id()); assertEquals(f.otherEncounter.id().value(), changed.encounterId());
        assertEquals(f.otherLevel.id().value(), changed.riskLevelId()); assertEquals(f.otherProfessional.id().value(), changed.assessedBy());
        assertTrue(changed.suicidalIdeation()); assertTrue(changed.suicidePlan()); assertTrue(changed.suicideIntent()); assertTrue(changed.selfHarm()); assertTrue(changed.harmToOthers());
        assertEquals("R2", changed.riskFactors()); assertEquals("P2", changed.protectiveFactors()); assertEquals("A2", changed.clinicalActions()); assertEquals("O2", changed.observations());
        assertEquals(f.time.plusDays(1), changed.assessedAt());
        var delete = new DeleteRiskAssessmentUseCase(f.repository); assertEquals(id, delete.execute(id).id()); assertTrue(f.repository.findAll().isEmpty());
        assertThrows(RiskAssessmentNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(RiskAssessmentNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(RiskAssessmentNotFoundApplicationException.class, () -> f.update.execute(f.updateCommand(id, 0)));
    }
    @Test void eachMissingParentPreventsRegistrationAndPartialUpdate() {
        var exceptions = List.of(EncounterNotFoundApplicationException.class, RiskLevelNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class);
        for (int i = 1; i <= 3; i++) {
            var f = new Fixture(); var original = f.register.execute(f.command(0)); var id = new RiskAssessmentId(original.id());
            var command = f.command(i); var update = f.updateCommand(id, i);
            assertThrows(exceptions.get(i - 1), () -> f.register.execute(command));
            assertThrows(exceptions.get(i - 1), () -> f.update.execute(update));
            assertEquals(original, new GetRiskAssessmentByIdUseCase(f.repository).execute(id)); assertEquals(1, f.repository.saves);
        }
    }
    @Test void repeatedEvaluationsAndInactiveLevelAreAllowedButNullDetailsCannotSave() {
        var f = new Fixture(); var original = f.register.execute(f.command(0)); var other = f.register.execute(f.command(0));
        assertNotEquals(original.id(), other.id()); assertFalse(f.level.active()); assertFalse(original.suicidalIdeation());
        var id = new RiskAssessmentId(original.id());
        assertThrows(NullPointerException.class, () -> f.register.execute(new RegisterRiskAssessmentCommand(f.encounter.id(), f.level.id(), false, false, false, false, false,
                "R", "P", "A", "O", null, f.professional.id())));
        assertThrows(NullPointerException.class, () -> f.update.execute(new UpdateRiskAssessmentCommand(id, f.otherEncounter.id(), f.level.id(), false, false, false, false, false,
                "R2", "P2", "A2", null, f.time, f.professional.id())));
        assertEquals(original, new GetRiskAssessmentByIdUseCase(f.repository).execute(id)); assertEquals(2, f.repository.saves);
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
        return Encounter.register(ClinicalRecordId.generate(), professional, EncounterTypeId.generate(), started, started.plusHours(1), "Motivo", "Condición",
                EncounterModalityId.generate(), EncounterStatusId.generate(), professional, professional);
    }
    private static class Fixture {
        final Professional professional = professional("PRO1"), otherProfessional = professional("PRO2");
        final Encounter encounter = encounter(professional.id()), otherEncounter = encounter(otherProfessional.id());
        final RiskLevel level = RiskLevel.register("LOW", "Nivel", false, 0), otherLevel = RiskLevel.register("HIGH", "Nivel", true, 2);
        final OffsetDateTime time = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
        final EncounterRepository encounters = parent(EncounterRepository.class, Map.of(encounter.id(), encounter, otherEncounter.id(), otherEncounter));
        final RiskLevelRepository levels = parent(RiskLevelRepository.class, Map.of(level.id(), level, otherLevel.id(), otherLevel));
        final ProfessionalRepository professionals = parent(ProfessionalRepository.class, Map.of(professional.id(), professional, otherProfessional.id(), otherProfessional));
        final Repository repository = new Repository();
        final RegisterRiskAssessmentUseCase register = new RegisterRiskAssessmentUseCase(repository, encounters, levels, professionals);
        final UpdateRiskAssessmentUseCase update = new UpdateRiskAssessmentUseCase(repository, encounters, levels, professionals);
        RegisterRiskAssessmentCommand command(int missing) {
            return new RegisterRiskAssessmentCommand(missing == 1 ? EncounterId.generate() : encounter.id(), missing == 2 ? RiskLevelId.generate() : level.id(),
                    false, false, false, false, false, "R", "P", "A", "O", time, missing == 3 ? ProfessionalId.generate() : professional.id());
        }
        UpdateRiskAssessmentCommand updateCommand(RiskAssessmentId id, int missing) {
            var c = command(missing);
            return new UpdateRiskAssessmentCommand(id, c.encounterId(), c.riskLevelId(), true, true, true, true, true, "R2", "P2", "A2", "O2", time, c.assessedBy());
        }
    }
    private static class Repository implements RiskAssessmentRepository {
        final Map<RiskAssessmentId, RiskAssessment> values = new LinkedHashMap<>(); int saves;
        public RiskAssessment save(RiskAssessment item) { saves++; values.put(item.id(), item); return item; }
        public Optional<RiskAssessment> findById(RiskAssessmentId id) { return Optional.ofNullable(values.get(id)); }
        public List<RiskAssessment> findAll() { return List.copyOf(values.values()); }
        public void delete(RiskAssessment item) { values.remove(item.id()); }
    }
}
