package com.migracion.rangel.application.chatmessage;
import java.lang.reflect.Proxy;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.chatmessage.command.*;
import com.migracion.rangel.application.chatmessage.usecase.*;
import com.migracion.rangel.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import com.migracion.rangel.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import com.migracion.rangel.domain.chatmessage.model.aggregate.ChatMessage;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.messagetype.model.aggregate.MessageType;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.domain.chatparticipant.model.aggregate.ChatParticipant;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
class ChatMessageUseCasesTest {
    private final ChatConversation conversation = ChatConversation.register(ConversationStatusId.generate(),PriorityId.generate(),null,null,null,null);
    private final MessageType type = MessageType.register("Texto");
    // V45 no exige que el participante pertenezca a la misma conversación.
    private final ChatParticipant participant = ChatParticipant.register(ChatConversationId.generate(),SenderTypeId.generate(),null,null);
    private final ChatConversationRepository conversations = lookup(ChatConversationRepository.class,Map.of(conversation.id(),conversation));
    private final MessageTypeRepository types = lookup(MessageTypeRepository.class,Map.of(type.id(),type));
    private final ChatParticipantRepository participants = lookup(ChatParticipantRepository.class,Map.of(participant.id(),participant));
    @Test
    void crudAllowsRepeatedMessagesAndKeepsCreatedAt() {
        var repository = new MemoryRepository();
        var command = new RegisterChatMessageCommand(conversation.id(),type.id(),participant.id(),"{}","{}");
        var first = register(repository).execute(command);
        var second = register(repository).execute(command);
        assertNotEquals(first.id(),second.id());
        var id = new ChatMessageId(first.id());
        var changed = update(repository).execute(new UpdateChatMessageCommand(id,conversation.id(),type.id(),participant.id(),"[]","null"));
        assertEquals(first.createdAt(),changed.createdAt());
        assertEquals("[]",changed.content());
        assertEquals("null",changed.metadata());
        assertEquals(changed,new GetChatMessageByIdUseCase(repository).execute(id));
        assertEquals(2,new ListChatMessageUseCase(repository).execute().size());
        assertEquals(id,new DeleteChatMessageUseCase(repository).execute(id).id());
        assertThrows(ChatMessageNotFoundApplicationException.class, () -> new GetChatMessageByIdUseCase(repository).execute(id));
        assertThrows(ChatMessageNotFoundApplicationException.class, () -> new DeleteChatMessageUseCase(repository).execute(id));
        assertThrows(ChatMessageNotFoundApplicationException.class, () -> update(repository).execute(new UpdateChatMessageCommand(id,conversation.id(),type.id(),participant.id(),"{}","{}")));
    }
    @Test
    void missingReferencesPreventRegistration() {
        var repository = new MemoryRepository();
        var register = register(repository);
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> register.execute(new RegisterChatMessageCommand(ChatConversationId.generate(),type.id(),participant.id(),"{}","{}")));
        assertThrows(MessageTypeNotFoundApplicationException.class, () -> register.execute(new RegisterChatMessageCommand(conversation.id(),MessageTypeId.generate(),participant.id(),"{}","{}")));
        assertThrows(ChatParticipantNotFoundApplicationException.class, () -> register.execute(new RegisterChatMessageCommand(conversation.id(),type.id(),ChatParticipantId.generate(),"{}","{}")));
        assertEquals(0,repository.saves);
    }
    @Test
    void missingReferencesPreventUpdateWithoutChangingRecord() {
        var repository = new MemoryRepository();
        var first = register(repository).execute(new RegisterChatMessageCommand(conversation.id(),type.id(),participant.id(),"{}","{}"));
        var id = new ChatMessageId(first.id());
        var update = update(repository);
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> update.execute(new UpdateChatMessageCommand(id,ChatConversationId.generate(),type.id(),participant.id(),"[]","[]")));
        assertThrows(MessageTypeNotFoundApplicationException.class, () -> update.execute(new UpdateChatMessageCommand(id,conversation.id(),MessageTypeId.generate(),participant.id(),"[]","[]")));
        assertThrows(ChatParticipantNotFoundApplicationException.class, () -> update.execute(new UpdateChatMessageCommand(id,conversation.id(),type.id(),ChatParticipantId.generate(),"[]","[]")));
        assertEquals(first,new GetChatMessageByIdUseCase(repository).execute(id));
        assertEquals(1,repository.saves);
    }
    private RegisterChatMessageUseCase register(MemoryRepository repository) { return new RegisterChatMessageUseCase(repository,conversations,types,participants); }
    private UpdateChatMessageUseCase update(MemoryRepository repository) { return new UpdateChatMessageUseCase(repository,conversations,types,participants); }
    private static <T> T lookup(Class<T> type,Map<?,?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(),new Class<?>[]{type},(proxy,method,args) -> {
            if (method.getName().equals("findById")) { return Optional.ofNullable(values.get(args[0])); }
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static class MemoryRepository implements ChatMessageRepository {
        private final Map<ChatMessageId,ChatMessage> values = new LinkedHashMap<>();
        private int saves;
        public ChatMessage save(ChatMessage value) { saves++; values.put(value.id(),value); return value; }
        public Optional<ChatMessage> findById(ChatMessageId id) { return Optional.ofNullable(values.get(id)); }
        public List<ChatMessage> findAll() { return List.copyOf(values.values()); }
        public void delete(ChatMessage value) { values.remove(value.id()); }
    }
}
