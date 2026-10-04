package com.migracion.rangel.infrastructure.chatairun;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatairun.model.aggregate.ChatAiRun;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.application.chatairun.dto.ChatAiRunResponse;
import com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.mappers.ChatAiRunPersistenceMapper;
class ChatAiRunPersistenceMapperTest {
    @Test
    void roundTripPreservesAllFieldsWithoutEvents() {
        var original = ChatAiRun.register(ChatConversationId.generate(),ChatMessageId.generate(),AiModelId.generate(),AiRunStatusId.generate());
        var mapper = new ChatAiRunPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ChatAiRunResponse.from(original),ChatAiRunResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
}
