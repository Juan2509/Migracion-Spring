package com.migracion.rangel.application.mentalstatusexam;
import java.util.*;
import java.time.OffsetDateTime;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.migracion.rangel.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;
import com.migracion.rangel.application.mentalstatusexam.command.*;
import com.migracion.rangel.application.mentalstatusexam.usecase.*;
import com.migracion.rangel.application.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
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
class MentalStatusExamUseCasesTest {
    @Test void crudChangesEncounterAndAllTextsWhilePreservingCreationAudit() throws ReflectiveOperationException {
        var f = new Fixture(); var original = f.register.execute(f.command(f.encounter.id(), f.professional.id()));
        var id = new MentalStatusExamId(original.id()); var get = new GetMentalStatusExamByIdUseCase(f.repository);
        assertEquals(original, get.execute(id)); assertEquals(List.of(original), new ListMentalStatusExamUseCase(f.repository).execute());
        var t = f.texts(); for (int i = 0; i < t.length; i++) t[i] = "Nuevo " + t[i];
        var changed = f.update.execute(new UpdateMentalStatusExamCommand(id, f.otherEncounter.id(), t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16]));
        assertEquals(original.createdAt(), changed.createdAt()); assertEquals(original.createdBy(), changed.createdBy());
        assertEquals(f.otherEncounter.id().value(), changed.encounterId());
        String[] fields = f.texts();
        for (int i = 0; i < fields.length; i++) assertEquals(t[i], changed.getClass().getMethod(fields[i]).invoke(changed));
        var delete = new DeleteMentalStatusExamUseCase(f.repository); assertEquals(id, delete.execute(id).id()); assertTrue(f.repository.findAll().isEmpty());
        assertThrows(MentalStatusExamNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(MentalStatusExamNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(MentalStatusExamNotFoundApplicationException.class, () -> f.update.execute(new UpdateMentalStatusExamCommand(id, f.encounter.id(), t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16])));
    }
    @Test void missingEncounterOrCreatorCannotSaveAndMissingEncounterCannotModify() {
        var f = new Fixture(); var original = f.register.execute(f.command(f.encounter.id(), f.professional.id())); var id = new MentalStatusExamId(original.id());
        assertThrows(EncounterNotFoundApplicationException.class, () -> f.register.execute(f.command(EncounterId.generate(), f.professional.id())));
        assertThrows(ProfessionalNotFoundApplicationException.class, () -> f.register.execute(f.command(f.encounter.id(), ProfessionalId.generate())));
        var t = f.texts();
        assertThrows(EncounterNotFoundApplicationException.class, () -> f.update.execute(new UpdateMentalStatusExamCommand(id, EncounterId.generate(), t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16])));
        assertEquals(original, new GetMentalStatusExamByIdUseCase(f.repository).execute(id)); assertEquals(1, f.repository.saves);
    }
    @Test void repeatedExamsAreAllowedAndMissingTextCannotPartiallyUpdate() {
        var f = new Fixture(); var original = f.register.execute(f.command(f.encounter.id(), f.professional.id()));
        var other = f.register.execute(f.command(f.encounter.id(), f.professional.id())); assertNotEquals(original.id(), other.id());
        var id = new MentalStatusExamId(original.id()); var t = f.texts(); t[16] = null;
        assertThrows(NullPointerException.class, () -> f.update.execute(new UpdateMentalStatusExamCommand(id, f.otherEncounter.id(), t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16])));
        assertEquals(original, new GetMentalStatusExamByIdUseCase(f.repository).execute(id)); assertEquals(2, f.repository.saves);
    }
    private static <T> T parent(Class<T> type, Map<?, ?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) return Optional.ofNullable(values.get(args[0]));
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static Encounter encounter(ProfessionalId professional) {
        var started = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
        return Encounter.register(ClinicalRecordId.generate(), professional, EncounterTypeId.generate(), started, started.plusHours(1), "Motivo", "Condición",
                EncounterModalityId.generate(), EncounterStatusId.generate(), professional, professional);
    }
    private static class Fixture {
        final Professional professional = Professional.register(DocumentTypeId.generate(), "PRO1", "Nombre", "Apellido", ProfessionalTypeId.generate(), "LIC1", true, CityMunicipalityId.generate());
        final Encounter encounter = encounter(professional.id()), otherEncounter = encounter(professional.id());
        final EncounterRepository encounters = parent(EncounterRepository.class, Map.of(encounter.id(), encounter, otherEncounter.id(), otherEncounter));
        final ProfessionalRepository professionals = parent(ProfessionalRepository.class, Map.of(professional.id(), professional));
        final Repository repository = new Repository();
        final RegisterMentalStatusExamUseCase register = new RegisterMentalStatusExamUseCase(repository, encounters, professionals);
        final UpdateMentalStatusExamUseCase update = new UpdateMentalStatusExamUseCase(repository, encounters);
        String[] texts() { return new String[]{"appearance", "behavior", "attitude", "consciousness", "orientation", "attention", "memory", "speech", "mood", "affect", "thoughtProcess", "thoughtContent", "perception", "judgment", "insight", "psychomotorActivity", "observations"}; }
        RegisterMentalStatusExamCommand command(EncounterId encounter, ProfessionalId creator) {
            var t = texts(); return new RegisterMentalStatusExamCommand(encounter, t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16], creator);
        }
    }
    private static class Repository implements MentalStatusExamRepository {
        final Map<MentalStatusExamId, MentalStatusExam> values = new LinkedHashMap<>(); int saves;
        public MentalStatusExam save(MentalStatusExam item) { saves++; values.put(item.id(), item); return item; }
        public Optional<MentalStatusExam> findById(MentalStatusExamId id) { return Optional.ofNullable(values.get(id)); }
        public List<MentalStatusExam> findAll() { return List.copyOf(values.values()); }
        public void delete(MentalStatusExam item) { values.remove(item.id()); }
    }
}
