package com.migracion.rangel.application.clinicalnote;
import java.util.*;
import java.time.OffsetDateTime;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.clinicalnote.command.*;
import com.migracion.rangel.application.clinicalnote.usecase.*;
import com.migracion.rangel.application.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.clinicalnote.model.aggregate.ClinicalNote;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
import com.migracion.rangel.domain.clinicalnote.port.repository.ClinicalNoteRepository;
import com.migracion.rangel.domain.encounter.model.aggregate.Encounter;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
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
class ClinicalNoteUseCasesTest {
    @Test void crudChangesBothReferencesAndAllClinicalFieldsPreservingCreation() {
        var f = new Fixture(); var original = f.register.execute(f.command(f.encounter.id(), f.professional.id()));
        var id = new ClinicalNoteId(original.id()); var get = new GetClinicalNoteByIdUseCase(f.repository);
        assertEquals(original, get.execute(id)); assertEquals(List.of(original), new ListClinicalNoteUseCase(f.repository).execute());
        var changed = f.update.execute(new UpdateClinicalNoteCommand(id, f.otherEncounter.id(), "S2", "O2", "A2", "P2", "N2", f.signed.plusDays(1), f.otherProfessional.id()));
        assertEquals(original.createdAt(), changed.createdAt()); assertEquals(f.otherEncounter.id().value(), changed.encounterId());
        assertEquals(f.otherProfessional.id().value(), changed.professionalId()); assertEquals("S2", changed.subjective());
        assertEquals("O2", changed.objective()); assertEquals("A2", changed.assessment()); assertEquals("P2", changed.plan()); assertEquals("N2", changed.additionalNotes());
        assertEquals(f.signed.plusDays(1), changed.signedAt());
        var delete = new DeleteClinicalNoteUseCase(f.repository); assertEquals(id, delete.execute(id).id()); assertTrue(f.repository.findAll().isEmpty());
        assertThrows(ClinicalNoteNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(ClinicalNoteNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(ClinicalNoteNotFoundApplicationException.class, () -> f.update.execute(
                new UpdateClinicalNoteCommand(id, f.encounter.id(), "S", "O", "A", "P", "N", f.signed, f.professional.id())));
    }
    @Test void missingEncounterOrProfessionalPreventsSaveAndPartialUpdate() {
        var exceptions = List.of(EncounterNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class);
        for (int i = 0; i < 2; i++) {
            var f = new Fixture(); var original = f.register.execute(f.command(f.encounter.id(), f.professional.id())); var id = new ClinicalNoteId(original.id());
            var encounter = i == 0 ? EncounterId.generate() : f.encounter.id(); var professional = i == 1 ? ProfessionalId.generate() : f.professional.id();
            assertThrows(exceptions.get(i), () -> f.register.execute(f.command(encounter, professional)));
            assertThrows(exceptions.get(i), () -> f.update.execute(new UpdateClinicalNoteCommand(id, encounter, "S2", "O2", "A2", "P2", "N2", f.signed, professional)));
            assertEquals(original, new GetClinicalNoteByIdUseCase(f.repository).execute(id)); assertEquals(1, f.repository.saves);
        }
    }
    @Test void repeatedNotesAreAllowedButMissingSignatureOrTextCannotSaveOrMutate() {
        var f = new Fixture(); var original = f.register.execute(f.command(f.encounter.id(), f.professional.id()));
        var second = f.register.execute(f.command(f.encounter.id(), f.professional.id())); assertNotEquals(original.id(), second.id());
        var id = new ClinicalNoteId(original.id());
        assertThrows(NullPointerException.class, () -> f.register.execute(new RegisterClinicalNoteCommand(f.encounter.id(), "S", "O", "A", "P", "N", null, f.professional.id())));
        assertThrows(NullPointerException.class, () -> f.update.execute(new UpdateClinicalNoteCommand(id, f.encounter.id(), "S2", "O2", "A2", "P2", null, f.signed, f.professional.id())));
        assertEquals(original, new GetClinicalNoteByIdUseCase(f.repository).execute(id)); assertEquals(2, f.repository.saves);
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
    private static Encounter encounter(ProfessionalId professional) {
        var started = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
        return Encounter.register(ClinicalRecordId.generate(), professional, EncounterTypeId.generate(), started, started.plusHours(1), "Motivo", "Condición",
                EncounterModalityId.generate(), EncounterStatusId.generate(), professional, professional);
    }
    private static class Fixture {
        final Professional professional = professional("PRO1"), otherProfessional = professional("PRO2");
        final Encounter encounter = encounter(professional.id()), otherEncounter = encounter(otherProfessional.id());
        final OffsetDateTime signed = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
        final EncounterRepository encounters = parent(EncounterRepository.class, Map.of(encounter.id(), encounter, otherEncounter.id(), otherEncounter));
        final ProfessionalRepository professionals = parent(ProfessionalRepository.class, Map.of(professional.id(), professional, otherProfessional.id(), otherProfessional));
        final Repository repository = new Repository();
        final RegisterClinicalNoteUseCase register = new RegisterClinicalNoteUseCase(repository, encounters, professionals);
        final UpdateClinicalNoteUseCase update = new UpdateClinicalNoteUseCase(repository, encounters, professionals);
        RegisterClinicalNoteCommand command(EncounterId encounter, ProfessionalId professional) {
            return new RegisterClinicalNoteCommand(encounter, "S", "O", "A", "P", "N", signed, professional);
        }
    }
    private static class Repository implements ClinicalNoteRepository {
        final Map<ClinicalNoteId, ClinicalNote> values = new LinkedHashMap<>(); int saves;
        public ClinicalNote save(ClinicalNote item) { saves++; values.put(item.id(), item); return item; }
        public Optional<ClinicalNote> findById(ClinicalNoteId id) { return Optional.ofNullable(values.get(id)); }
        public List<ClinicalNote> findAll() { return List.copyOf(values.values()); }
        public void delete(ClinicalNote item) { values.remove(item.id()); }
    }
}
