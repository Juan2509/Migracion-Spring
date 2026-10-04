package com.migracion.rangel.application.treatmentgoalstatus;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.treatmentgoalstatus.command.*;
import com.migracion.rangel.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.exception.DuplicateTreatmentGoalStatusApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.*;
import com.migracion.rangel.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

class TreatmentGoalStatusUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterTreatmentGoalStatusUseCase(repository);
        var get = new GetTreatmentGoalStatusByIdUseCase(repository);
        var list = new ListTreatmentGoalStatusUseCase(repository);
        var update = new UpdateTreatmentGoalStatusUseCase(repository);
        var delete = new DeleteTreatmentGoalStatusUseCase(repository);
        var response = register.execute(new RegisterTreatmentGoalStatusCommand("INITIAL", "Inicial", true));
        var id = new TreatmentGoalStatusId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateTreatmentGoalStatusCommand(id, "Otro code", "Otro name", false));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro code", changed.code());
        assertEquals("Otro name", changed.name());
        assertEquals(false, changed.active());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(TreatmentGoalStatusNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(TreatmentGoalStatusNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(TreatmentGoalStatusNotFoundApplicationException.class, () -> update.execute(new UpdateTreatmentGoalStatusCommand(id, "INITIAL", "Inicial", true)));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterTreatmentGoalStatusUseCase(repository);
        var update = new UpdateTreatmentGoalStatusUseCase(repository);
        var first = register.execute(new RegisterTreatmentGoalStatusCommand("INITIAL", "Inicial", true));
        var second = register.execute(new RegisterTreatmentGoalStatusCommand("Otro code", "Otro name", false));
        assertThrows(DuplicateTreatmentGoalStatusApplicationException.class,
                () -> register.execute(new RegisterTreatmentGoalStatusCommand("INITIAL", "Inicial", true)));
        assertThrows(DuplicateTreatmentGoalStatusApplicationException.class,
                () -> register.execute(new RegisterTreatmentGoalStatusCommand("INITIAL", "Nombre diferente", true)));
        assertThrows(DuplicateTreatmentGoalStatusApplicationException.class,
                () -> register.execute(new RegisterTreatmentGoalStatusCommand("DIFFERENT", "Inicial", true)));
        var firstId = new TreatmentGoalStatusId(first.id());
        var secondId = new TreatmentGoalStatusId(second.id());
        var unchanged = update.execute(new UpdateTreatmentGoalStatusCommand(firstId, "INITIAL", "Inicial", true));
        assertEquals(first.code(), unchanged.code());
        assertThrows(DuplicateTreatmentGoalStatusApplicationException.class,
                () -> update.execute(new UpdateTreatmentGoalStatusCommand(secondId, first.code(), first.name(), first.active())));
        assertThrows(DuplicateTreatmentGoalStatusApplicationException.class,
                () -> update.execute(new UpdateTreatmentGoalStatusCommand(secondId, first.code(), "Nombre diferente", true)));
        assertThrows(DuplicateTreatmentGoalStatusApplicationException.class,
                () -> update.execute(new UpdateTreatmentGoalStatusCommand(secondId, "DIFFERENT", first.name(), true)));
        assertEquals(second, new GetTreatmentGoalStatusByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    @Test
    void invalidUpdateDoesNotSaveOrMutateExistingCatalog() {
        var repository = new MemoryRepository();
        var original = new RegisterTreatmentGoalStatusUseCase(repository).execute(new RegisterTreatmentGoalStatusCommand("INITIAL", "Inicial", false));
        var id = new TreatmentGoalStatusId(original.id());
        assertThrows(IllegalArgumentException.class, () -> new UpdateTreatmentGoalStatusUseCase(repository).execute(
                new UpdateTreatmentGoalStatusCommand(id, "NEW", "x".repeat(51), true)));
        assertEquals(original, new GetTreatmentGoalStatusByIdUseCase(repository).execute(id));
        assertEquals(1, repository.saves);
    }

    private static class MemoryRepository implements TreatmentGoalStatusRepository {
        private final Map<TreatmentGoalStatusId, TreatmentGoalStatus> values = new LinkedHashMap<>();
        private int saves;
        public TreatmentGoalStatus save(TreatmentGoalStatus aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<TreatmentGoalStatus> findById(TreatmentGoalStatusId id) { return Optional.ofNullable(values.get(id)); }
        public List<TreatmentGoalStatus> findAll() { return List.copyOf(values.values()); }
        public void delete(TreatmentGoalStatus aggregate) { values.remove(aggregate.id()); }
        public boolean existsByCode(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.code().equals(value));
        }
        public boolean existsByCodeAndIdNot(String value, TreatmentGoalStatusId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.code().equals(value));
        }
        public boolean existsByName(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.name().equals(value));
        }
        public boolean existsByNameAndIdNot(String value, TreatmentGoalStatusId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.name().equals(value));
        }
    }
}



