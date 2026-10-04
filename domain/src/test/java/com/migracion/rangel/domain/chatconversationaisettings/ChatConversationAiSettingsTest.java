package com.migracion.rangel.domain.chatconversationaisettings;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.migracion.rangel.domain.chatconversationaisettings.event.*;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
class ChatConversationAiSettingsTest {
    @Test
    void restoreAndUpdateKeepIdentityCreationDateAndEvents() {
        var conversation = ChatConversationId.generate();
        var model = AiModelId.generate();
        var original = ChatConversationAiSettings.register(conversation, false, model);
        assertInstanceOf(ChatConversationAiSettingsRegisteredEvent.class, original.domainEvents().getFirst());
        var created = LocalDateTime.of(2020,1,1,0,0);
        var restored = ChatConversationAiSettings.restore(original.id(), conversation, false, model, created, created);
        assertTrue(restored.domainEvents().isEmpty());
        var nextModel = AiModelId.generate();
        restored.update(conversation, true, nextModel);
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertEquals(nextModel, restored.defaultModelId());
        assertTrue(restored.aiEnabled());
        assertInstanceOf(ChatConversationAiSettingsUpdatedEvent.class, restored.domainEvents().getFirst());
    }
    @Test
    void missingRequiredFieldsPreventRegistrationAndPartialUpdate() {
        var conversation = ChatConversationId.generate();
        var model = AiModelId.generate();
        var aggregate = ChatConversationAiSettings.register(conversation, false, model);
        var updatedAt = aggregate.updatedAt();
        assertThrows(NullPointerException.class, () -> aggregate.update(ChatConversationId.generate(), true, null));
        assertEquals(conversation, aggregate.conversationId());
        assertEquals(model, aggregate.defaultModelId());
        assertFalse(aggregate.aiEnabled());
        assertEquals(updatedAt, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> ChatConversationAiSettings.register(null, false, model));
        assertThrows(NullPointerException.class, () -> ChatConversationAiSettings.register(conversation, null, model));
        assertThrows(NullPointerException.class, () -> ChatConversationAiSettings.register(conversation, false, null));
    }
}
