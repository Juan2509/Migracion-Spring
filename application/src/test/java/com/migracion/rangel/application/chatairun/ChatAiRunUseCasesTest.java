package com.migracion.rangel.application.chatairun;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.chatairun.command.*;
import com.migracion.rangel.application.chatairun.usecase.*;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import com.migracion.rangel.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import com.migracion.rangel.domain.chatairun.model.aggregate.ChatAiRun;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.chatmessage.model.aggregate.ChatMessage;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.aimodel.model.aggregate.AiModel;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.domain.airunstatus.model.aggregate.AiRunStatus;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
class ChatAiRunUseCasesTest {
    private final ChatConversation conversation = ChatConversation.register(ConversationStatusId.generate(),PriorityId.generate(),null,null,null,null);
    private final ChatMessage message = ChatMessage.register(ChatConversationId.generate(),MessageTypeId.generate(),ChatParticipantId.generate(),"{}","{}");
    private final AiModel model = AiModel.register("p","Modelo","key",BigDecimal.ZERO,BigDecimal.ZERO,1,1,false);
    private final AiRunStatus status = AiRunStatus.register("Pendiente");
    private final ChatConversationRepository conversations = lookup(ChatConversationRepository.class,Map.of(conversation.id(),conversation));
    private final ChatMessageRepository messages = lookup(ChatMessageRepository.class,Map.of(message.id(),message));
    private final AiModelRepository models = lookup(AiModelRepository.class,Map.of(model.id(),model));
    private final AiRunStatusRepository statuses = lookup(AiRunStatusRepository.class,Map.of(status.id(),status));
    @Test
    void crudAllowsRepeatedRunsWithoutAddingBusinessRules() {
        var repository = new MemoryRepository();
        var register = register(repository);
        var command = new RegisterChatAiRunCommand(conversation.id(),message.id(),model.id(),status.id());
        var first = register.execute(command);
        var second = register.execute(command);
        assertNotEquals(first.id(),second.id());
        var id = new ChatAiRunId(first.id());
        var changed = update(repository).execute(new UpdateChatAiRunCommand(id,conversation.id(),message.id(),model.id(),status.id()));
        assertEquals(first.createdAt(),changed.createdAt());
        assertEquals(changed,new GetChatAiRunByIdUseCase(repository).execute(id));
        assertEquals(2,new ListChatAiRunUseCase(repository).execute().size());
        assertEquals(id,new DeleteChatAiRunUseCase(repository).execute(id).id());
        assertThrows(ChatAiRunNotFoundApplicationException.class, () -> new GetChatAiRunByIdUseCase(repository).execute(id));
        assertThrows(ChatAiRunNotFoundApplicationException.class, () -> new DeleteChatAiRunUseCase(repository).execute(id));
        assertThrows(ChatAiRunNotFoundApplicationException.class, () -> update(repository).execute(new UpdateChatAiRunCommand(id,conversation.id(),message.id(),model.id(),status.id())));
    }
    @Test
    void allMissingReferencesPreventRegistration() {
        var repository = new MemoryRepository();
        var register = register(repository);
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> register.execute(new RegisterChatAiRunCommand(ChatConversationId.generate(),message.id(),model.id(),status.id())));
        assertThrows(ChatMessageNotFoundApplicationException.class, () -> register.execute(new RegisterChatAiRunCommand(conversation.id(),ChatMessageId.generate(),model.id(),status.id())));
        assertThrows(AiModelNotFoundApplicationException.class, () -> register.execute(new RegisterChatAiRunCommand(conversation.id(),message.id(),AiModelId.generate(),status.id())));
        assertThrows(AiRunStatusNotFoundApplicationException.class, () -> register.execute(new RegisterChatAiRunCommand(conversation.id(),message.id(),model.id(),AiRunStatusId.generate())));
        assertEquals(0,repository.saves);
    }
    @Test
    void allMissingReferencesPreventUpdateWithoutChangingRecord() {
        var repository = new MemoryRepository();
        var first = register(repository).execute(new RegisterChatAiRunCommand(conversation.id(),message.id(),model.id(),status.id()));
        var id = new ChatAiRunId(first.id());
        var update = update(repository);
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> update.execute(new UpdateChatAiRunCommand(id,ChatConversationId.generate(),message.id(),model.id(),status.id())));
        assertThrows(ChatMessageNotFoundApplicationException.class, () -> update.execute(new UpdateChatAiRunCommand(id,conversation.id(),ChatMessageId.generate(),model.id(),status.id())));
        assertThrows(AiModelNotFoundApplicationException.class, () -> update.execute(new UpdateChatAiRunCommand(id,conversation.id(),message.id(),AiModelId.generate(),status.id())));
        assertThrows(AiRunStatusNotFoundApplicationException.class, () -> update.execute(new UpdateChatAiRunCommand(id,conversation.id(),message.id(),model.id(),AiRunStatusId.generate())));
        assertEquals(first,new GetChatAiRunByIdUseCase(repository).execute(id));
        assertEquals(1,repository.saves);
    }
    private RegisterChatAiRunUseCase register(MemoryRepository repository) { return new RegisterChatAiRunUseCase(repository,conversations,messages,models,statuses); }
    private UpdateChatAiRunUseCase update(MemoryRepository repository) { return new UpdateChatAiRunUseCase(repository,conversations,messages,models,statuses); }
    private static <T> T lookup(Class<T> type,Map<?,?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(),new Class<?>[]{type},(proxy,method,args) -> {
            if (method.getName().equals("findById")) { return Optional.ofNullable(values.get(args[0])); }
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static class MemoryRepository implements ChatAiRunRepository {
        private final Map<ChatAiRunId,ChatAiRun> values = new LinkedHashMap<>();
        private int saves;
        public ChatAiRun save(ChatAiRun value) { saves++; values.put(value.id(),value); return value; }
        public Optional<ChatAiRun> findById(ChatAiRunId id) { return Optional.ofNullable(values.get(id)); }
        public List<ChatAiRun> findAll() { return List.copyOf(values.values()); }
        public void delete(ChatAiRun value) { values.remove(value.id()); }
    }
}
