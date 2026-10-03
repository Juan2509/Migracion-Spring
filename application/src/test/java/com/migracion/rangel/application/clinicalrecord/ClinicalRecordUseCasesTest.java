package com.migracion.rangel.application.clinicalrecord;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.clinicalrecord.command.*;
import com.migracion.rangel.application.clinicalrecord.usecase.*;
import com.migracion.rangel.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import com.migracion.rangel.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.migracion.rangel.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import com.migracion.rangel.domain.patient.model.aggregate.Patient;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
class ClinicalRecordUseCasesTest {
    @Test void crudCanChangeReferencesAndClinicalDatesWhileKeepingCreatorAndCreationAudit() {
        var f = new Fixture();
        var original = f.register.execute(f.command(f.patient.id(), f.status.id(), f.professional.id()));
        var id = new ClinicalRecordId(original.id());
        var get = new GetClinicalRecordByIdUseCase(f.repository);
        assertEquals(original, get.execute(id));
        assertEquals(List.of(original), new ListClinicalRecordUseCase(f.repository).execute());
        var changed = f.update.execute(new UpdateClinicalRecordCommand(id, f.otherPatient.id(), f.creation.plusDays(1), "HC-2",
                f.opened.plusDays(1), f.opened.plusDays(2), f.otherStatus.id()));
        assertEquals(original.id(), changed.id()); assertEquals(original.createdAt(), changed.createdAt()); assertEquals(original.createdBy(), changed.createdBy());
        assertEquals(f.otherPatient.id().value(), changed.patientId()); assertEquals(f.otherStatus.id().value(), changed.statusId());
        assertEquals(f.creation.plusDays(1), changed.creationDate()); assertEquals(f.opened.plusDays(2), changed.closedAt());
        var delete = new DeleteClinicalRecordUseCase(f.repository);
        assertEquals(id, delete.execute(id).id()); assertTrue(f.repository.findAll().isEmpty());
        assertThrows(ClinicalRecordNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(ClinicalRecordNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(ClinicalRecordNotFoundApplicationException.class, () -> f.update.execute(
                new UpdateClinicalRecordCommand(id, f.patient.id(), f.creation, "HC-1", f.opened, f.opened.plusHours(1), f.status.id())));
    }
    @Test void missingPatientStatusOrCreatorCannotSaveAndFailedUpdateCannotMutate() {
        var exceptions = List.of(PatientNotFoundApplicationException.class, ClinicalRecordStatusNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class);
        for (int i = 0; i < 3; i++) {
            var f = new Fixture();
            var original = f.register.execute(f.command(f.patient.id(), f.status.id(), f.professional.id()));
            var id = new ClinicalRecordId(original.id());
            var patient = i == 0 ? PatientId.generate() : f.patient.id();
            var status = i == 1 ? ClinicalRecordStatusId.generate() : f.status.id();
            var creator = i == 2 ? ProfessionalId.generate() : f.professional.id();
            assertThrows(exceptions.get(i), () -> f.register.execute(f.command(patient, status, creator)));
            if (i < 2) {
                assertThrows(exceptions.get(i), () -> f.update.execute(
                        new UpdateClinicalRecordCommand(id, patient, f.creation, "Nueva", f.opened, f.opened.plusHours(1), status)));
            }
            assertEquals(original, new GetClinicalRecordByIdUseCase(f.repository).execute(id)); assertEquals(1, f.repository.saves);
        }
    }
    @Test void repeatedRecordNumbersAreAllowedAndClosingDateCannotBeOmitted() {
        var f = new Fixture();
        var first = f.register.execute(f.command(f.patient.id(), f.status.id(), f.professional.id()));
        var second = f.register.execute(f.command(f.patient.id(), f.status.id(), f.professional.id()));
        assertNotEquals(first.id(), second.id()); assertEquals(first.recordNumber(), second.recordNumber());
        var id = new ClinicalRecordId(first.id());
        assertThrows(NullPointerException.class, () -> f.register.execute(new RegisterClinicalRecordCommand(
                f.patient.id(), f.creation, "Nueva", f.opened, null, f.status.id(), f.professional.id())));
        assertThrows(NullPointerException.class, () -> f.update.execute(new UpdateClinicalRecordCommand(
                id, f.patient.id(), f.creation, "Nueva", f.opened, null, f.status.id())));
        assertEquals(first, new GetClinicalRecordByIdUseCase(f.repository).execute(id)); assertEquals(2, f.repository.saves);
    }
    private static <T> T parent(Class<T> type, Map<?, ?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) return Optional.ofNullable(values.get(args[0]));
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static Patient patient(String email) {
        return Patient.register(DocumentTypeId.generate(), "DOC1", "Ana", null, "Pérez", null, LocalDate.of(1990, 1, 2),
                GenderId.generate(), GenderId.generate(), email, "300123", "Calle 1", true, CityMunicipalityId.generate(), null);
    }
    private static class Fixture {
        final Patient patient = patient("ana@example.com"), otherPatient = patient("otra@example.com");
        final ClinicalRecordStatus status = ClinicalRecordStatus.register("OPEN", "Abierta"), otherStatus = ClinicalRecordStatus.register("CLOSED", "Cerrada");
        final Professional professional = Professional.register(DocumentTypeId.generate(), "PRO1", "Nombre", "Apellido", ProfessionalTypeId.generate(), "LIC1", true, CityMunicipalityId.generate());
        final LocalDateTime creation = LocalDateTime.parse("2026-10-03T08:00:00");
        final OffsetDateTime opened = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
        final PatientRepository patients = parent(PatientRepository.class, Map.of(patient.id(), patient, otherPatient.id(), otherPatient));
        final ClinicalRecordStatusRepository statuses = parent(ClinicalRecordStatusRepository.class, Map.of(status.id(), status, otherStatus.id(), otherStatus));
        final ProfessionalRepository professionals = parent(ProfessionalRepository.class, Map.of(professional.id(), professional));
        final Repository repository = new Repository();
        final RegisterClinicalRecordUseCase register = new RegisterClinicalRecordUseCase(repository, patients, statuses, professionals);
        final UpdateClinicalRecordUseCase update = new UpdateClinicalRecordUseCase(repository, patients, statuses);
        RegisterClinicalRecordCommand command(PatientId patientId, ClinicalRecordStatusId statusId, ProfessionalId creator) {
            return new RegisterClinicalRecordCommand(patientId, creation, "HC-1", opened, opened.plusHours(1), statusId, creator);
        }
    }
    private static class Repository implements ClinicalRecordRepository {
        final Map<ClinicalRecordId, ClinicalRecord> values = new LinkedHashMap<>(); int saves;
        public ClinicalRecord save(ClinicalRecord item) { saves++; values.put(item.id(), item); return item; }
        public Optional<ClinicalRecord> findById(ClinicalRecordId id) { return Optional.ofNullable(values.get(id)); }
        public List<ClinicalRecord> findAll() { return List.copyOf(values.values()); }
        public void delete(ClinicalRecord item) { values.remove(item.id()); }
    }
}
