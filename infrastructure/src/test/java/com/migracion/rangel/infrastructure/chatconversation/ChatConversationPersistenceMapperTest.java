package com.migracion.rangel.infrastructure.chatconversation;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.application.chatconversation.dto.ChatConversationResponse;
import com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.mappers.ChatConversationPersistenceMapper;
class ChatConversationPersistenceMapperTest {
    @Test
    void roundTripPreservesPopulatedFieldsWithoutEvents() {
        var now = LocalDateTime.now();
        roundTrip(ChatConversation.register(ConversationStatusId.generate(), PriorityId.generate(), now, true, now, UUID.randomUUID()));
    }
    @Test
    void roundTripPreservesNullOptionalFieldsWithoutEvents() {
        roundTrip(ChatConversation.register(ConversationStatusId.generate(), PriorityId.generate(), null, null, null, null));
    }
    private void roundTrip(ChatConversation original) {
        var mapper = new ChatConversationPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ChatConversationResponse.from(original), ChatConversationResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
}
