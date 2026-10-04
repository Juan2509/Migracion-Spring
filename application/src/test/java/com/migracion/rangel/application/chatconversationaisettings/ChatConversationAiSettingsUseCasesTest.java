package com.migracion.rangel.application.chatconversationaisettings;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.chatconversationaisettings.command.*;
import com.migracion.rangel.application.chatconversationaisettings.usecase.*;
import com.migracion.rangel.application.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundApplicationException;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import com.migracion.rangel.domain.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.migracion.rangel.domain.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.aimodel.model.aggregate.AiModel;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
class ChatConversationAiSettingsUseCasesTest {
    private final ChatConversation conversation = ChatConversation.register(ConversationStatusId.generate(), PriorityId.generate(), null, null, null, null);
    private final AiModel model = AiModel.register("texto", "Modelo", "key", BigDecimal.ZERO, BigDecimal.ZERO, 1, 1, false);
    private final ChatConversationRepository conversations = lookup(ChatConversationRepository.class, Map.of(conversation.id(),conversation));
    private final AiModelRepository models = lookup(AiModelRepository.class, Map.of(model.id(),model));
    @Test
    void crudAllowsMultipleSettingsAndDisabledDefaultModel() {
        var repository = new MemoryRepository();
        var register = register(repository);
        var command = new RegisterChatConversationAiSettingsCommand(conversation.id(), false, model.id());
        var first = register.execute(command);
        var second = register.execute(command);
        assertNotEquals(first.id(), second.id());
        var id = new ChatConversationAiSettingsId(first.id());
        var changed = update(repository).execute(new UpdateChatConversationAiSettingsCommand(id, conversation.id(), true, model.id()));
        assertEquals(first.createdAt(), changed.createdAt());
        assertTrue(changed.aiEnabled());
        assertEquals(changed, new GetChatConversationAiSettingsByIdUseCase(repository).execute(id));
        assertEquals(2, new ListChatConversationAiSettingsUseCase(repository).execute().size());
        assertEquals(id, new DeleteChatConversationAiSettingsUseCase(repository).execute(id).id());
        assertThrows(ChatConversationAiSettingsNotFoundApplicationException.class, () -> new GetChatConversationAiSettingsByIdUseCase(repository).execute(id));
        assertThrows(ChatConversationAiSettingsNotFoundApplicationException.class, () -> new DeleteChatConversationAiSettingsUseCase(repository).execute(id));
        assertThrows(ChatConversationAiSettingsNotFoundApplicationException.class, () -> update(repository).execute(new UpdateChatConversationAiSettingsCommand(id, conversation.id(), false, model.id())));
    }
    @Test
    void missingReferencesPreventRegistrationEvenWhenAiDisabled() {
        var repository = new MemoryRepository();
        var register = register(repository);
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> register.execute(new RegisterChatConversationAiSettingsCommand(ChatConversationId.generate(), false, model.id())));
        assertThrows(AiModelNotFoundApplicationException.class, () -> register.execute(new RegisterChatConversationAiSettingsCommand(conversation.id(), false, AiModelId.generate())));
        assertEquals(0, repository.saves);
        assertTrue(repository.findAll().isEmpty());
    }
    @Test
    void missingReferencesPreventUpdateWithoutChangingRecord() {
        var repository = new MemoryRepository();
        var first = register(repository).execute(new RegisterChatConversationAiSettingsCommand(conversation.id(), false, model.id()));
        var id = new ChatConversationAiSettingsId(first.id());
        var update = update(repository);
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> update.execute(new UpdateChatConversationAiSettingsCommand(id, ChatConversationId.generate(), true, model.id())));
        assertThrows(AiModelNotFoundApplicationException.class, () -> update.execute(new UpdateChatConversationAiSettingsCommand(id, conversation.id(), true, AiModelId.generate())));
        assertEquals(first, new GetChatConversationAiSettingsByIdUseCase(repository).execute(id));
        assertEquals(1, repository.saves);
    }
    private RegisterChatConversationAiSettingsUseCase register(MemoryRepository repository) {
        return new RegisterChatConversationAiSettingsUseCase(repository, conversations, models);
    }
    private UpdateChatConversationAiSettingsUseCase update(MemoryRepository repository) {
        return new UpdateChatConversationAiSettingsUseCase(repository, conversations, models);
    }
    private static <T> T lookup(Class<T> type, Map<?,?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) { return Optional.ofNullable(values.get(args[0])); }
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static class MemoryRepository implements ChatConversationAiSettingsRepository {
        private final Map<ChatConversationAiSettingsId,ChatConversationAiSettings> values = new LinkedHashMap<>();
        private int saves;
        public ChatConversationAiSettings save(ChatConversationAiSettings value) { saves++; values.put(value.id(),value); return value; }
        public Optional<ChatConversationAiSettings> findById(ChatConversationAiSettingsId id) { return Optional.ofNullable(values.get(id)); }
        public List<ChatConversationAiSettings> findAll() { return List.copyOf(values.values()); }
        public void delete(ChatConversationAiSettings value) { values.remove(value.id()); }
    }
}
