package com.migracion.rangel.application.encountermodality;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.encountermodality.command.*;
import com.migracion.rangel.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.migracion.rangel.application.encountermodality.exception.DuplicateEncounterModalityApplicationException;
import com.migracion.rangel.application.encountermodality.usecase.*;
import com.migracion.rangel.domain.encountermodality.model.aggregate.EncounterModality;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;

class EncounterModalityUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterEncounterModalityUseCase(repository);
        var get = new GetEncounterModalityByIdUseCase(repository);
        var list = new ListEncounterModalityUseCase(repository);
        var update = new UpdateEncounterModalityUseCase(repository);
        var delete = new DeleteEncounterModalityUseCase(repository);
        var response = register.execute(new RegisterEncounterModalityCommand("INITIAL", "Inicial", true));
        var id = new EncounterModalityId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateEncounterModalityCommand(id, "Otro code", "Otro name", false));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro code", changed.code());
        assertEquals("Otro name", changed.name());
        assertEquals(false, changed.active());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(EncounterModalityNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(EncounterModalityNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(EncounterModalityNotFoundApplicationException.class, () -> update.execute(new UpdateEncounterModalityCommand(id, "INITIAL", "Inicial", true)));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterEncounterModalityUseCase(repository);
        var update = new UpdateEncounterModalityUseCase(repository);
        var first = register.execute(new RegisterEncounterModalityCommand("INITIAL", "Inicial", true));
        var second = register.execute(new RegisterEncounterModalityCommand("Otro code", "Otro name", false));
        assertThrows(DuplicateEncounterModalityApplicationException.class,
                () -> register.execute(new RegisterEncounterModalityCommand("INITIAL", "Inicial", true)));
        assertThrows(DuplicateEncounterModalityApplicationException.class,
                () -> register.execute(new RegisterEncounterModalityCommand("INITIAL", "Nombre diferente", true)));
        assertThrows(DuplicateEncounterModalityApplicationException.class,
                () -> register.execute(new RegisterEncounterModalityCommand("DIFFERENT", "Inicial", true)));
        var firstId = new EncounterModalityId(first.id());
        var secondId = new EncounterModalityId(second.id());
        var unchanged = update.execute(new UpdateEncounterModalityCommand(firstId, "INITIAL", "Inicial", true));
        assertEquals(first.code(), unchanged.code());
        assertThrows(DuplicateEncounterModalityApplicationException.class,
                () -> update.execute(new UpdateEncounterModalityCommand(secondId, first.code(), first.name(), first.active())));
        assertThrows(DuplicateEncounterModalityApplicationException.class,
                () -> update.execute(new UpdateEncounterModalityCommand(secondId, first.code(), "Nombre diferente", true)));
        assertThrows(DuplicateEncounterModalityApplicationException.class,
                () -> update.execute(new UpdateEncounterModalityCommand(secondId, "DIFFERENT", first.name(), true)));
        assertEquals(second, new GetEncounterModalityByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    @Test
    void invalidUpdateDoesNotSaveOrMutateExistingCatalog() {
        var repository = new MemoryRepository();
        var original = new RegisterEncounterModalityUseCase(repository).execute(new RegisterEncounterModalityCommand("INITIAL", "Inicial", false));
        var id = new EncounterModalityId(original.id());
        assertThrows(IllegalArgumentException.class, () -> new UpdateEncounterModalityUseCase(repository).execute(
                new UpdateEncounterModalityCommand(id, "NEW", "x".repeat(51), true)));
        assertEquals(original, new GetEncounterModalityByIdUseCase(repository).execute(id));
        assertEquals(1, repository.saves);
    }

    private static class MemoryRepository implements EncounterModalityRepository {
        private final Map<EncounterModalityId, EncounterModality> values = new LinkedHashMap<>();
        private int saves;
        public EncounterModality save(EncounterModality aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<EncounterModality> findById(EncounterModalityId id) { return Optional.ofNullable(values.get(id)); }
        public List<EncounterModality> findAll() { return List.copyOf(values.values()); }
        public void delete(EncounterModality aggregate) { values.remove(aggregate.id()); }
        public boolean existsByCode(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.code().equals(value));
        }
        public boolean existsByCodeAndIdNot(String value, EncounterModalityId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.code().equals(value));
        }
        public boolean existsByName(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.name().equals(value));
        }
        public boolean existsByNameAndIdNot(String value, EncounterModalityId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.name().equals(value));
        }
    }
}

