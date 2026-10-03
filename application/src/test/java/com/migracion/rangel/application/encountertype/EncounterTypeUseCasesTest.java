package com.migracion.rangel.application.encountertype;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.encountertype.command.*;
import com.migracion.rangel.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.migracion.rangel.application.encountertype.exception.DuplicateEncounterTypeApplicationException;
import com.migracion.rangel.application.encountertype.usecase.*;
import com.migracion.rangel.domain.encountertype.model.aggregate.EncounterType;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.domain.encountertype.port.repository.EncounterTypeRepository;

class EncounterTypeUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterEncounterTypeUseCase(repository);
        var get = new GetEncounterTypeByIdUseCase(repository);
        var list = new ListEncounterTypeUseCase(repository);
        var update = new UpdateEncounterTypeUseCase(repository);
        var delete = new DeleteEncounterTypeUseCase(repository);
        var response = register.execute(new RegisterEncounterTypeCommand("INITIAL", "Inicial", true));
        var id = new EncounterTypeId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateEncounterTypeCommand(id, "Otro code", "Otro name", false));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro code", changed.code());
        assertEquals("Otro name", changed.name());
        assertEquals(false, changed.active());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(EncounterTypeNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(EncounterTypeNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(EncounterTypeNotFoundApplicationException.class, () -> update.execute(new UpdateEncounterTypeCommand(id, "INITIAL", "Inicial", true)));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterEncounterTypeUseCase(repository);
        var update = new UpdateEncounterTypeUseCase(repository);
        var first = register.execute(new RegisterEncounterTypeCommand("INITIAL", "Inicial", true));
        var second = register.execute(new RegisterEncounterTypeCommand("Otro code", "Otro name", false));
        assertThrows(DuplicateEncounterTypeApplicationException.class,
                () -> register.execute(new RegisterEncounterTypeCommand("INITIAL", "Inicial", true)));
        assertThrows(DuplicateEncounterTypeApplicationException.class,
                () -> register.execute(new RegisterEncounterTypeCommand("INITIAL", "Nombre diferente", true)));
        assertThrows(DuplicateEncounterTypeApplicationException.class,
                () -> register.execute(new RegisterEncounterTypeCommand("DIFFERENT", "Inicial", true)));
        var firstId = new EncounterTypeId(first.id());
        var secondId = new EncounterTypeId(second.id());
        var unchanged = update.execute(new UpdateEncounterTypeCommand(firstId, "INITIAL", "Inicial", true));
        assertEquals(first.code(), unchanged.code());
        assertThrows(DuplicateEncounterTypeApplicationException.class,
                () -> update.execute(new UpdateEncounterTypeCommand(secondId, first.code(), first.name(), first.active())));
        assertThrows(DuplicateEncounterTypeApplicationException.class,
                () -> update.execute(new UpdateEncounterTypeCommand(secondId, first.code(), "Nombre diferente", true)));
        assertThrows(DuplicateEncounterTypeApplicationException.class,
                () -> update.execute(new UpdateEncounterTypeCommand(secondId, "DIFFERENT", first.name(), true)));
        assertEquals(second, new GetEncounterTypeByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    @Test
    void invalidUpdateDoesNotSaveOrMutateExistingCatalog() {
        var repository = new MemoryRepository();
        var original = new RegisterEncounterTypeUseCase(repository).execute(new RegisterEncounterTypeCommand("INITIAL", "Inicial", false));
        var id = new EncounterTypeId(original.id());
        assertThrows(IllegalArgumentException.class, () -> new UpdateEncounterTypeUseCase(repository).execute(
                new UpdateEncounterTypeCommand(id, "NEW", "x".repeat(51), true)));
        assertEquals(original, new GetEncounterTypeByIdUseCase(repository).execute(id));
        assertEquals(1, repository.saves);
    }

    private static class MemoryRepository implements EncounterTypeRepository {
        private final Map<EncounterTypeId, EncounterType> values = new LinkedHashMap<>();
        private int saves;
        public EncounterType save(EncounterType aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<EncounterType> findById(EncounterTypeId id) { return Optional.ofNullable(values.get(id)); }
        public List<EncounterType> findAll() { return List.copyOf(values.values()); }
        public void delete(EncounterType aggregate) { values.remove(aggregate.id()); }
        public boolean existsByCode(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.code().equals(value));
        }
        public boolean existsByCodeAndIdNot(String value, EncounterTypeId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.code().equals(value));
        }
        public boolean existsByName(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.name().equals(value));
        }
        public boolean existsByNameAndIdNot(String value, EncounterTypeId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.name().equals(value));
        }
    }
}

