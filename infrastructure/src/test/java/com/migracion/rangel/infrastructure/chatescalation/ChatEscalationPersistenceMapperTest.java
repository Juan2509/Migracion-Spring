package com.migracion.rangel.infrastructure.chatescalation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatescalation.model.aggregate.ChatEscalation;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.application.chatescalation.dto.ChatEscalationResponse;
import com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.mappers.ChatEscalationPersistenceMapper;
class ChatEscalationPersistenceMapperTest {
    @Test
    void roundTripPreservesAllFieldsWithoutEvents() {
        var original = ChatEscalation.register(ChatConversationId.generate(),"Motivo",EscalationStatusId.generate(),false);
        var mapper = new ChatEscalationPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ChatEscalationResponse.from(original),ChatEscalationResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
}
