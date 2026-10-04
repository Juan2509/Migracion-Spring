package com.migracion.rangel.infrastructure.chatconversationaisettings;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.application.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.mappers.ChatConversationAiSettingsPersistenceMapper;
class ChatConversationAiSettingsPersistenceMapperTest {
    @Test
    void roundTripPreservesAllFieldsWithoutEvents() {
        var original = ChatConversationAiSettings.register(ChatConversationId.generate(), false, AiModelId.generate());
        var mapper = new ChatConversationAiSettingsPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ChatConversationAiSettingsResponse.from(original), ChatConversationAiSettingsResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
}
