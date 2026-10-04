package com.migracion.rangel.application.airunstatus;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.airunstatus.command.*;
import com.migracion.rangel.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import com.migracion.rangel.application.airunstatus.usecase.*;
import com.migracion.rangel.domain.airunstatus.model.aggregate.AiRunStatus;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;

class AiRunStatusUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingAiRunStatus() {
        var repository = new MemoryRepository();
        var register = new RegisterAiRunStatusUseCase(repository);
        var get = new GetAiRunStatusByIdUseCase(repository);
        var list = new ListAiRunStatusUseCase(repository);
        var update = new UpdateAiRunStatusUseCase(repository);
        var delete = new DeleteAiRunStatusUseCase(repository);
        var response = register.execute(new RegisterAiRunStatusCommand("Psicología"));
        var id = new AiRunStatusId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateAiRunStatusCommand(id, "Psiquiatría"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Psiquiatría", changed.nameStatus());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(AiRunStatusNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(AiRunStatusNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(AiRunStatusNotFoundApplicationException.class, () -> update.execute(new UpdateAiRunStatusCommand(id, "Psicología")));
    }
    @Test
    void repeatedNamesAreAllowedOnRegisterAndUpdate() {
        var repository = new MemoryRepository();
        var register = new RegisterAiRunStatusUseCase(repository);
        var first = register.execute(new RegisterAiRunStatusCommand("Psicología"));
        var second = register.execute(new RegisterAiRunStatusCommand("Psicología"));
        assertNotEquals(first.id(), second.id());
        var third = register.execute(new RegisterAiRunStatusCommand("Medicina"));
        var changed = new UpdateAiRunStatusUseCase(repository).execute(
                new UpdateAiRunStatusCommand(new AiRunStatusId(third.id()), "Psicología"));
        assertEquals("Psicología", changed.nameStatus());
        assertEquals(3, new ListAiRunStatusUseCase(repository).execute().size());
    }
    private static class MemoryRepository implements AiRunStatusRepository {
        private final Map<AiRunStatusId, AiRunStatus> values = new LinkedHashMap<>();
        public AiRunStatus save(AiRunStatus aggregate) { values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<AiRunStatus> findById(AiRunStatusId id) { return Optional.ofNullable(values.get(id)); }
        public List<AiRunStatus> findAll() { return List.copyOf(values.values()); }
        public void delete(AiRunStatus aggregate) { values.remove(aggregate.id()); }
    }
}
