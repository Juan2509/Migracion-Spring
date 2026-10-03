package com.migracion.rangel.application.encounterstatus;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.encounterstatus.command.*;
import com.migracion.rangel.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.migracion.rangel.application.encounterstatus.exception.DuplicateEncounterStatusApplicationException;
import com.migracion.rangel.application.encounterstatus.usecase.*;
import com.migracion.rangel.domain.encounterstatus.model.aggregate.EncounterStatus;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;

class EncounterStatusUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterEncounterStatusUseCase(repository);
        var get = new GetEncounterStatusByIdUseCase(repository);
        var list = new ListEncounterStatusUseCase(repository);
        var update = new UpdateEncounterStatusUseCase(repository);
        var delete = new DeleteEncounterStatusUseCase(repository);
        var response = register.execute(new RegisterEncounterStatusCommand("INITIAL", "Inicial", true));
        var id = new EncounterStatusId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateEncounterStatusCommand(id, "Otro code", "Otro name", false));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro code", changed.code());
        assertEquals("Otro name", changed.name());
        assertEquals(false, changed.active());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(EncounterStatusNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(EncounterStatusNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(EncounterStatusNotFoundApplicationException.class, () -> update.execute(new UpdateEncounterStatusCommand(id, "INITIAL", "Inicial", true)));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterEncounterStatusUseCase(repository);
        var update = new UpdateEncounterStatusUseCase(repository);
        var first = register.execute(new RegisterEncounterStatusCommand("INITIAL", "Inicial", true));
        var second = register.execute(new RegisterEncounterStatusCommand("Otro code", "Otro name", false));
        assertThrows(DuplicateEncounterStatusApplicationException.class,
                () -> register.execute(new RegisterEncounterStatusCommand("INITIAL", "Inicial", true)));
        assertThrows(DuplicateEncounterStatusApplicationException.class,
                () -> register.execute(new RegisterEncounterStatusCommand("INITIAL", "Nombre diferente", true)));
        assertThrows(DuplicateEncounterStatusApplicationException.class,
                () -> register.execute(new RegisterEncounterStatusCommand("DIFFERENT", "Inicial", true)));
        var firstId = new EncounterStatusId(first.id());
        var secondId = new EncounterStatusId(second.id());
        var unchanged = update.execute(new UpdateEncounterStatusCommand(firstId, "INITIAL", "Inicial", true));
        assertEquals(first.code(), unchanged.code());
        assertThrows(DuplicateEncounterStatusApplicationException.class,
                () -> update.execute(new UpdateEncounterStatusCommand(secondId, first.code(), first.name(), first.active())));
        assertThrows(DuplicateEncounterStatusApplicationException.class,
                () -> update.execute(new UpdateEncounterStatusCommand(secondId, first.code(), "Nombre diferente", true)));
        assertThrows(DuplicateEncounterStatusApplicationException.class,
                () -> update.execute(new UpdateEncounterStatusCommand(secondId, "DIFFERENT", first.name(), true)));
        assertEquals(second, new GetEncounterStatusByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    @Test
    void invalidUpdateDoesNotSaveOrMutateExistingCatalog() {
        var repository = new MemoryRepository();
        var original = new RegisterEncounterStatusUseCase(repository).execute(new RegisterEncounterStatusCommand("INITIAL", "Inicial", false));
        var id = new EncounterStatusId(original.id());
        assertThrows(IllegalArgumentException.class, () -> new UpdateEncounterStatusUseCase(repository).execute(
                new UpdateEncounterStatusCommand(id, "NEW", "x".repeat(51), true)));
        assertEquals(original, new GetEncounterStatusByIdUseCase(repository).execute(id));
        assertEquals(1, repository.saves);
    }

    private static class MemoryRepository implements EncounterStatusRepository {
        private final Map<EncounterStatusId, EncounterStatus> values = new LinkedHashMap<>();
        private int saves;
        public EncounterStatus save(EncounterStatus aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<EncounterStatus> findById(EncounterStatusId id) { return Optional.ofNullable(values.get(id)); }
        public List<EncounterStatus> findAll() { return List.copyOf(values.values()); }
        public void delete(EncounterStatus aggregate) { values.remove(aggregate.id()); }
        public boolean existsByCode(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.code().equals(value));
        }
        public boolean existsByCodeAndIdNot(String value, EncounterStatusId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.code().equals(value));
        }
        public boolean existsByName(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.name().equals(value));
        }
        public boolean existsByNameAndIdNot(String value, EncounterStatusId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.name().equals(value));
        }
    }
}

