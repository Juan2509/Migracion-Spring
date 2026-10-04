package com.migracion.rangel.application.chatescalation;
import java.lang.reflect.Proxy;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.chatescalation.command.*;
import com.migracion.rangel.application.chatescalation.usecase.*;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import com.migracion.rangel.domain.chatescalation.model.aggregate.ChatEscalation;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.escalationstatus.model.aggregate.EscalationStatus;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
class ChatEscalationUseCasesTest {
    private final ChatConversation conversation = ChatConversation.register(ConversationStatusId.generate(),PriorityId.generate(),null,null,null,null);
    private final EscalationStatus status = EscalationStatus.register("Pendiente");
    private final ChatConversationRepository conversations = lookup(ChatConversationRepository.class,Map.of(conversation.id(),conversation));
    private final EscalationStatusRepository statuses = lookup(EscalationStatusRepository.class,Map.of(status.id(),status));
    @Test
    void crudAllowsRepeatedEscalationsAndKeepsCreationDate() {
        var repository = new MemoryRepository();
        var register = register(repository);
        var command = new RegisterChatEscalationCommand(conversation.id(),"Motivo",status.id(),false);
        var first = register.execute(command);
        var second = register.execute(command);
        assertNotEquals(first.id(),second.id());
        var id = new ChatEscalationId(first.id());
        var changed = update(repository).execute(new UpdateChatEscalationCommand(id,conversation.id(),"Nuevo",status.id(),true));
        assertEquals(first.createdAt(),changed.createdAt());
        assertEquals("Nuevo",changed.reason());
        assertTrue(changed.fromAi());
        assertEquals(changed,new GetChatEscalationByIdUseCase(repository).execute(id));
        assertEquals(2,new ListChatEscalationUseCase(repository).execute().size());
        assertEquals(id,new DeleteChatEscalationUseCase(repository).execute(id).id());
        assertThrows(ChatEscalationNotFoundApplicationException.class, () -> new GetChatEscalationByIdUseCase(repository).execute(id));
        assertThrows(ChatEscalationNotFoundApplicationException.class, () -> new DeleteChatEscalationUseCase(repository).execute(id));
        assertThrows(ChatEscalationNotFoundApplicationException.class, () -> update(repository).execute(new UpdateChatEscalationCommand(id,conversation.id(),"r",status.id(),false)));
    }
    @Test
    void missingReferencesPreventRegistration() {
        var repository = new MemoryRepository();
        var register = register(repository);
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> register.execute(new RegisterChatEscalationCommand(ChatConversationId.generate(),"r",status.id(),false)));
        assertThrows(EscalationStatusNotFoundApplicationException.class, () -> register.execute(new RegisterChatEscalationCommand(conversation.id(),"r",EscalationStatusId.generate(),false)));
        assertEquals(0,repository.saves);
    }
    @Test
    void missingReferencesPreventUpdateWithoutChangingRecord() {
        var repository = new MemoryRepository();
        var first = register(repository).execute(new RegisterChatEscalationCommand(conversation.id(),"Original",status.id(),false));
        var id = new ChatEscalationId(first.id());
        var update = update(repository);
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> update.execute(new UpdateChatEscalationCommand(id,ChatConversationId.generate(),"Nuevo",status.id(),true)));
        assertThrows(EscalationStatusNotFoundApplicationException.class, () -> update.execute(new UpdateChatEscalationCommand(id,conversation.id(),"Nuevo",EscalationStatusId.generate(),true)));
        assertEquals(first,new GetChatEscalationByIdUseCase(repository).execute(id));
        assertEquals(1,repository.saves);
    }
    private RegisterChatEscalationUseCase register(MemoryRepository repository) { return new RegisterChatEscalationUseCase(repository,conversations,statuses); }
    private UpdateChatEscalationUseCase update(MemoryRepository repository) { return new UpdateChatEscalationUseCase(repository,conversations,statuses); }
    private static <T> T lookup(Class<T> type,Map<?,?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(),new Class<?>[]{type},(proxy,method,args) -> {
            if (method.getName().equals("findById")) { return Optional.ofNullable(values.get(args[0])); }
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static class MemoryRepository implements ChatEscalationRepository {
        private final Map<ChatEscalationId,ChatEscalation> values = new LinkedHashMap<>();
        private int saves;
        public ChatEscalation save(ChatEscalation value) { saves++; values.put(value.id(),value); return value; }
        public Optional<ChatEscalation> findById(ChatEscalationId id) { return Optional.ofNullable(values.get(id)); }
        public List<ChatEscalation> findAll() { return List.copyOf(values.values()); }
        public void delete(ChatEscalation value) { values.remove(value.id()); }
    }
}
