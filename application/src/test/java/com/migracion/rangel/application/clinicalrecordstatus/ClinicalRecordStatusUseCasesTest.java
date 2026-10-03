package com.migracion.rangel.application.clinicalrecordstatus;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.clinicalrecordstatus.command.*;
import com.migracion.rangel.application.clinicalrecordstatus.usecase.*;
import com.migracion.rangel.application.clinicalrecordstatus.exception.*;
import com.migracion.rangel.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
class ClinicalRecordStatusUseCasesTest {
    @Test void fullCrudAllowsOwnUniqueValuesAndReportsMissingStatus() {
        var repo = new Repository();
        var original = new RegisterClinicalRecordStatusUseCase(repo).execute(new RegisterClinicalRecordStatusCommand("OPEN", "Abierta"));
        var id = new ClinicalRecordStatusId(original.id());
        var get = new GetClinicalRecordStatusByIdUseCase(repo);
        assertEquals(original, get.execute(id)); assertEquals(List.of(original), new ListClinicalRecordStatusUseCase(repo).execute());
        var changed = new UpdateClinicalRecordStatusUseCase(repo).execute(new UpdateClinicalRecordStatusCommand(id, "OPEN", "Abierta"));
        assertEquals(original.createdAt(), changed.createdAt()); assertEquals(original.id(), changed.id());
        var delete = new DeleteClinicalRecordStatusUseCase(repo);
        assertEquals(id, delete.execute(id).id()); assertTrue(repo.findAll().isEmpty());
        assertThrows(ClinicalRecordStatusNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(ClinicalRecordStatusNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(ClinicalRecordStatusNotFoundApplicationException.class, () -> new UpdateClinicalRecordStatusUseCase(repo).execute(new UpdateClinicalRecordStatusCommand(id, "OPEN", "Abierta")));
    }
    @Test void codeAndNameAreIndependentlyUniqueOnRegistration() {
        var repo = new Repository(); var register = new RegisterClinicalRecordStatusUseCase(repo);
        register.execute(new RegisterClinicalRecordStatusCommand("OPEN", "Abierta"));
        assertThrows(DuplicateClinicalRecordStatusApplicationException.class, () -> register.execute(new RegisterClinicalRecordStatusCommand("OPEN", "Nueva")));
        assertThrows(DuplicateClinicalRecordStatusApplicationException.class, () -> register.execute(new RegisterClinicalRecordStatusCommand("NEW", "Abierta")));
        assertEquals(1, repo.saves);
    }
    @Test void duplicateCodeOrNameCannotPartiallyModifyExistingStatus() {
        var repo = new Repository(); var register = new RegisterClinicalRecordStatusUseCase(repo);
        register.execute(new RegisterClinicalRecordStatusCommand("OPEN", "Abierta"));
        var original = register.execute(new RegisterClinicalRecordStatusCommand("CLOSED", "Cerrada"));
        var id = new ClinicalRecordStatusId(original.id()); var update = new UpdateClinicalRecordStatusUseCase(repo);
        assertThrows(DuplicateClinicalRecordStatusApplicationException.class, () -> update.execute(new UpdateClinicalRecordStatusCommand(id, "OPEN", "Otra")));
        assertThrows(DuplicateClinicalRecordStatusApplicationException.class, () -> update.execute(new UpdateClinicalRecordStatusCommand(id, "OTHER", "Abierta")));
        assertEquals(original, new GetClinicalRecordStatusByIdUseCase(repo).execute(id)); assertEquals(2, repo.saves);
    }
    private static class Repository implements ClinicalRecordStatusRepository {
        final Map<ClinicalRecordStatusId, ClinicalRecordStatus> values = new LinkedHashMap<>(); int saves;
        public ClinicalRecordStatus save(ClinicalRecordStatus item) { saves++; values.put(item.id(), item); return item; }
        public Optional<ClinicalRecordStatus> findById(ClinicalRecordStatusId id) { return Optional.ofNullable(values.get(id)); }
        public List<ClinicalRecordStatus> findAll() { return List.copyOf(values.values()); }
        public void delete(ClinicalRecordStatus item) { values.remove(item.id()); }
        public boolean existsByCode(String code) { return values.values().stream().anyMatch(p -> p.code().equals(code)); }
        public boolean existsByName(String name) { return values.values().stream().anyMatch(p -> p.name().equals(name)); }
        public boolean existsByCodeAndIdNot(String code, ClinicalRecordStatusId id) { return values.values().stream().anyMatch(p -> p.code().equals(code) && !p.id().equals(id)); }
        public boolean existsByNameAndIdNot(String name, ClinicalRecordStatusId id) { return values.values().stream().anyMatch(p -> p.name().equals(name) && !p.id().equals(id)); }
    }
}
