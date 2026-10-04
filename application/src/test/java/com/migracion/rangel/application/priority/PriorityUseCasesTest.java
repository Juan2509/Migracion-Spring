package com.migracion.rangel.application.priority;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.priority.command.*;
import com.migracion.rangel.application.priority.exception.PriorityNotFoundApplicationException;
import com.migracion.rangel.application.priority.usecase.*;
import com.migracion.rangel.domain.priority.model.aggregate.Priority;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;

class PriorityUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingPriority() {
        var repository = new MemoryRepository();
        var register = new RegisterPriorityUseCase(repository);
        var get = new GetPriorityByIdUseCase(repository);
        var list = new ListPriorityUseCase(repository);
        var update = new UpdatePriorityUseCase(repository);
        var delete = new DeletePriorityUseCase(repository);
        var response = register.execute(new RegisterPriorityCommand("Psicología"));
        var id = new PriorityId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdatePriorityCommand(id, "Psiquiatría"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Psiquiatría", changed.namePriority());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(PriorityNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(PriorityNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(PriorityNotFoundApplicationException.class, () -> update.execute(new UpdatePriorityCommand(id, "Psicología")));
    }
    @Test
    void repeatedNamesAreAllowedOnRegisterAndUpdate() {
        var repository = new MemoryRepository();
        var register = new RegisterPriorityUseCase(repository);
        var first = register.execute(new RegisterPriorityCommand("Psicología"));
        var second = register.execute(new RegisterPriorityCommand("Psicología"));
        assertNotEquals(first.id(), second.id());
        var third = register.execute(new RegisterPriorityCommand("Medicina"));
        var changed = new UpdatePriorityUseCase(repository).execute(
                new UpdatePriorityCommand(new PriorityId(third.id()), "Psicología"));
        assertEquals("Psicología", changed.namePriority());
        assertEquals(3, new ListPriorityUseCase(repository).execute().size());
    }
    private static class MemoryRepository implements PriorityRepository {
        private final Map<PriorityId, Priority> values = new LinkedHashMap<>();
        public Priority save(Priority aggregate) { values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<Priority> findById(PriorityId id) { return Optional.ofNullable(values.get(id)); }
        public List<Priority> findAll() { return List.copyOf(values.values()); }
        public void delete(Priority aggregate) { values.remove(aggregate.id()); }
    }
}
