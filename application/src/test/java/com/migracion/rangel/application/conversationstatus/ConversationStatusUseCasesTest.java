package com.migracion.rangel.application.conversationstatus;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.conversationstatus.command.*;
import com.migracion.rangel.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import com.migracion.rangel.application.conversationstatus.usecase.*;
import com.migracion.rangel.domain.conversationstatus.model.aggregate.ConversationStatus;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;

class ConversationStatusUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingConversationStatus() {
        var repository = new MemoryRepository();
        var register = new RegisterConversationStatusUseCase(repository);
        var get = new GetConversationStatusByIdUseCase(repository);
        var list = new ListConversationStatusUseCase(repository);
        var update = new UpdateConversationStatusUseCase(repository);
        var delete = new DeleteConversationStatusUseCase(repository);
        var response = register.execute(new RegisterConversationStatusCommand("Psicología"));
        var id = new ConversationStatusId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateConversationStatusCommand(id, "Psiquiatría"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Psiquiatría", changed.nameStatus());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(ConversationStatusNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(ConversationStatusNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(ConversationStatusNotFoundApplicationException.class, () -> update.execute(new UpdateConversationStatusCommand(id, "Psicología")));
    }
    @Test
    void repeatedNamesAreAllowedOnRegisterAndUpdate() {
        var repository = new MemoryRepository();
        var register = new RegisterConversationStatusUseCase(repository);
        var first = register.execute(new RegisterConversationStatusCommand("Psicología"));
        var second = register.execute(new RegisterConversationStatusCommand("Psicología"));
        assertNotEquals(first.id(), second.id());
        var third = register.execute(new RegisterConversationStatusCommand("Medicina"));
        var changed = new UpdateConversationStatusUseCase(repository).execute(
                new UpdateConversationStatusCommand(new ConversationStatusId(third.id()), "Psicología"));
        assertEquals("Psicología", changed.nameStatus());
        assertEquals(3, new ListConversationStatusUseCase(repository).execute().size());
    }
    private static class MemoryRepository implements ConversationStatusRepository {
        private final Map<ConversationStatusId, ConversationStatus> values = new LinkedHashMap<>();
        public ConversationStatus save(ConversationStatus aggregate) { values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<ConversationStatus> findById(ConversationStatusId id) { return Optional.ofNullable(values.get(id)); }
        public List<ConversationStatus> findAll() { return List.copyOf(values.values()); }
        public void delete(ConversationStatus aggregate) { values.remove(aggregate.id()); }
    }
}
