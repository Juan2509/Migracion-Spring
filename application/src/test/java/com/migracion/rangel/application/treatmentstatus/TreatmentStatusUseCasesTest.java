package com.migracion.rangel.application.treatmentstatus;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.treatmentstatus.command.*;
import com.migracion.rangel.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentstatus.exception.DuplicateTreatmentStatusApplicationException;
import com.migracion.rangel.application.treatmentstatus.usecase.*;
import com.migracion.rangel.domain.treatmentstatus.model.aggregate.TreatmentStatus;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;

class TreatmentStatusUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterTreatmentStatusUseCase(repository);
        var get = new GetTreatmentStatusByIdUseCase(repository);
        var list = new ListTreatmentStatusUseCase(repository);
        var update = new UpdateTreatmentStatusUseCase(repository);
        var delete = new DeleteTreatmentStatusUseCase(repository);
        var response = register.execute(new RegisterTreatmentStatusCommand("INITIAL", "Inicial", true));
        var id = new TreatmentStatusId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateTreatmentStatusCommand(id, "Otro code", "Otro name", false));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro code", changed.code());
        assertEquals("Otro name", changed.name());
        assertEquals(false, changed.active());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(TreatmentStatusNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(TreatmentStatusNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(TreatmentStatusNotFoundApplicationException.class, () -> update.execute(new UpdateTreatmentStatusCommand(id, "INITIAL", "Inicial", true)));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterTreatmentStatusUseCase(repository);
        var update = new UpdateTreatmentStatusUseCase(repository);
        var first = register.execute(new RegisterTreatmentStatusCommand("INITIAL", "Inicial", true));
        var second = register.execute(new RegisterTreatmentStatusCommand("Otro code", "Otro name", false));
        assertThrows(DuplicateTreatmentStatusApplicationException.class,
                () -> register.execute(new RegisterTreatmentStatusCommand("INITIAL", "Inicial", true)));
        assertThrows(DuplicateTreatmentStatusApplicationException.class,
                () -> register.execute(new RegisterTreatmentStatusCommand("INITIAL", "Nombre diferente", true)));
        assertThrows(DuplicateTreatmentStatusApplicationException.class,
                () -> register.execute(new RegisterTreatmentStatusCommand("DIFFERENT", "Inicial", true)));
        var firstId = new TreatmentStatusId(first.id());
        var secondId = new TreatmentStatusId(second.id());
        var unchanged = update.execute(new UpdateTreatmentStatusCommand(firstId, "INITIAL", "Inicial", true));
        assertEquals(first.code(), unchanged.code());
        assertThrows(DuplicateTreatmentStatusApplicationException.class,
                () -> update.execute(new UpdateTreatmentStatusCommand(secondId, first.code(), first.name(), first.active())));
        assertThrows(DuplicateTreatmentStatusApplicationException.class,
                () -> update.execute(new UpdateTreatmentStatusCommand(secondId, first.code(), "Nombre diferente", true)));
        assertThrows(DuplicateTreatmentStatusApplicationException.class,
                () -> update.execute(new UpdateTreatmentStatusCommand(secondId, "DIFFERENT", first.name(), true)));
        assertEquals(second, new GetTreatmentStatusByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    @Test
    void invalidUpdateDoesNotSaveOrMutateExistingCatalog() {
        var repository = new MemoryRepository();
        var original = new RegisterTreatmentStatusUseCase(repository).execute(new RegisterTreatmentStatusCommand("INITIAL", "Inicial", false));
        var id = new TreatmentStatusId(original.id());
        assertThrows(IllegalArgumentException.class, () -> new UpdateTreatmentStatusUseCase(repository).execute(
                new UpdateTreatmentStatusCommand(id, "NEW", "x".repeat(51), true)));
        assertEquals(original, new GetTreatmentStatusByIdUseCase(repository).execute(id));
        assertEquals(1, repository.saves);
    }

    private static class MemoryRepository implements TreatmentStatusRepository {
        private final Map<TreatmentStatusId, TreatmentStatus> values = new LinkedHashMap<>();
        private int saves;
        public TreatmentStatus save(TreatmentStatus aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<TreatmentStatus> findById(TreatmentStatusId id) { return Optional.ofNullable(values.get(id)); }
        public List<TreatmentStatus> findAll() { return List.copyOf(values.values()); }
        public void delete(TreatmentStatus aggregate) { values.remove(aggregate.id()); }
        public boolean existsByCode(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.code().equals(value));
        }
        public boolean existsByCodeAndIdNot(String value, TreatmentStatusId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.code().equals(value));
        }
        public boolean existsByName(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.name().equals(value));
        }
        public boolean existsByNameAndIdNot(String value, TreatmentStatusId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.name().equals(value));
        }
    }
}


