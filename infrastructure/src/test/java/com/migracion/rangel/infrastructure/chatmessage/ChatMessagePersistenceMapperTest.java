package com.migracion.rangel.infrastructure.chatmessage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tools.jackson.databind.json.JsonMapper;
import com.migracion.rangel.domain.chatmessage.model.aggregate.ChatMessage;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.application.chatmessage.dto.ChatMessageResponse;
import com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.mappers.ChatMessagePersistenceMapper;
import com.migracion.rangel.infrastructure.chatmessage.adapters.in.rest.dtos.ChatMessageRestResponse;
class ChatMessagePersistenceMapperTest {
    private ChatMessage message() {
        return ChatMessage.register(ChatConversationId.generate(),MessageTypeId.generate(),ChatParticipantId.generate(),
                "{\"text\":\"Hola\",\"items\":[1,true]}", "null");
    }
    @Test
    void roundTripPreservesFieldsAndJsonWithoutEvents() {
        var original = message();
        var mapper = new ChatMessagePersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ChatMessageResponse.from(original),ChatMessageResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
    @Test
    void restResponseSerializesStructuredJsonWithoutDoubleEncoding() {
        var json = JsonMapper.builder().build();
        var response = ChatMessageRestResponse.from(ChatMessageResponse.from(message()));
        var document = json.readTree(json.writeValueAsString(response));
        assertTrue(document.get("content").isObject());
        assertTrue(document.get("content").get("items").isArray());
        assertEquals("Hola",document.get("content").get("text").asString());
        assertTrue(document.get("metadata").isNull());
        assertNull(document.get("updatedAt"));
    }
}
