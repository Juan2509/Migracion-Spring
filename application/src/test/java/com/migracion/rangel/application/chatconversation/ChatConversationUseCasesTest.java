package com.migracion.rangel.application.chatconversation;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.chatconversation.command.*;
import com.migracion.rangel.application.chatconversation.usecase.*;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import com.migracion.rangel.application.priority.exception.PriorityNotFoundApplicationException;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.conversationstatus.model.aggregate.ConversationStatus;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;
import com.migracion.rangel.domain.priority.model.aggregate.Priority;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;
class ChatConversationUseCasesTest {
    private final ConversationStatus status = ConversationStatus.register("Abierta");
    private final Priority priority = Priority.register("Normal");
    private final ConversationStatusRepository statuses = lookup(ConversationStatusRepository.class, status.id(), status);
    private final PriorityRepository priorities = lookup(PriorityRepository.class, priority.id(), priority);
    @Test
    void crudPreservesAuditAndSupportsClearingOptionalFields() {
        var repository = new MemoryRepository();
        var register = new RegisterChatConversationUseCase(repository, statuses, priorities);
        var command = new RegisterChatConversationCommand(status.id(), priority.id(), null, null, null, null);
        var first = register.execute(command);
        var second = register.execute(command);
        assertNotEquals(first.id(), second.id());
        var id = new ChatConversationId(first.id());
        var now = LocalDateTime.now();
        var arbitraryCloser = UUID.randomUUID();
        var update = new UpdateChatConversationUseCase(repository, statuses, priorities);
        var changed = update.execute(new UpdateChatConversationCommand(id, status.id(), priority.id(), now, true, now, arbitraryCloser));
        assertEquals(first.createdAt(), changed.createdAt());
        assertEquals(arbitraryCloser, changed.closedBy());
        var cleared = update.execute(new UpdateChatConversationCommand(id, status.id(), priority.id(), null, null, null, null));
        assertNull(cleared.closed());
        assertNull(cleared.closedAt());
        assertNull(cleared.closedBy());
        assertEquals(cleared, new GetChatConversationByIdUseCase(repository).execute(id));
        assertEquals(2, new ListChatConversationUseCase(repository).execute().size());
        assertEquals(id, new DeleteChatConversationUseCase(repository).execute(id).id());
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> new GetChatConversationByIdUseCase(repository).execute(id));
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> new DeleteChatConversationUseCase(repository).execute(id));
        assertThrows(ChatConversationNotFoundApplicationException.class,
                () -> update.execute(new UpdateChatConversationCommand(id, status.id(), priority.id(), null, null, null, null)));
    }
    @Test
    void missingReferencesPreventRegistration() {
        var repository = new MemoryRepository();
        var register = new RegisterChatConversationUseCase(repository, statuses, priorities);
        assertThrows(ConversationStatusNotFoundApplicationException.class, () -> register.execute(
                new RegisterChatConversationCommand(ConversationStatusId.generate(), priority.id(), null, null, null, null)));
        assertThrows(PriorityNotFoundApplicationException.class, () -> register.execute(
                new RegisterChatConversationCommand(status.id(), PriorityId.generate(), null, null, null, null)));
        assertEquals(0, repository.saves);
        assertTrue(repository.findAll().isEmpty());
    }
    @Test
    void missingReferencesPreventUpdateWithoutChangingStoredRecord() {
        var repository = new MemoryRepository();
        var response = new RegisterChatConversationUseCase(repository, statuses, priorities).execute(
                new RegisterChatConversationCommand(status.id(), priority.id(), null, null, null, null));
        var id = new ChatConversationId(response.id());
        var update = new UpdateChatConversationUseCase(repository, statuses, priorities);
        assertThrows(ConversationStatusNotFoundApplicationException.class, () -> update.execute(
                new UpdateChatConversationCommand(id, ConversationStatusId.generate(), priority.id(), null, true, null, null)));
        assertThrows(PriorityNotFoundApplicationException.class, () -> update.execute(
                new UpdateChatConversationCommand(id, status.id(), PriorityId.generate(), null, true, null, null)));
        assertEquals(response, new GetChatConversationByIdUseCase(repository).execute(id));
        assertEquals(1, repository.saves);
    }
    private static <T> T lookup(Class<T> type, Object id, Object value) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) {
                return Objects.equals(id, args[0]) ? Optional.of(value) : Optional.empty();
            }
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static class MemoryRepository implements ChatConversationRepository {
        private final Map<ChatConversationId,ChatConversation> values = new LinkedHashMap<>();
        private int saves;
        public ChatConversation save(ChatConversation value) { saves++; values.put(value.id(),value); return value; }
        public Optional<ChatConversation> findById(ChatConversationId id) { return Optional.ofNullable(values.get(id)); }
        public List<ChatConversation> findAll() { return List.copyOf(values.values()); }
        public void delete(ChatConversation value) { values.remove(value.id()); }
    }
}
