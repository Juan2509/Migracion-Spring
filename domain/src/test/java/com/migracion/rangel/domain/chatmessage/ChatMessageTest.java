package com.migracion.rangel.domain.chatmessage;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatmessage.model.aggregate.ChatMessage;
import com.migracion.rangel.domain.chatmessage.event.*;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
class ChatMessageTest {
    @Test
    void updateAndRestorePreserveCreationDateWithoutAddingUpdateAudit() {
        var conversation = ChatConversationId.generate();
        var type = MessageTypeId.generate();
        var participant = ChatParticipantId.generate();
        var original = ChatMessage.register(conversation,type,participant,"{\"text\":\"Hola\"}","{}");
        assertInstanceOf(ChatMessageRegisteredEvent.class, original.domainEvents().getFirst());
        var created = LocalDateTime.of(2020,1,1,0,0);
        var restored = ChatMessage.restore(original.id(),conversation,type,participant,original.content(),original.metadata(),created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update(conversation,type,participant,"[1,true]","null");
        assertEquals(original.id(),restored.id());
        assertEquals(created,restored.createdAt());
        assertEquals("[1,true]",restored.content());
        assertEquals("null",restored.metadata());
        assertInstanceOf(ChatMessageUpdatedEvent.class,restored.domainEvents().getFirst());
    }
    @Test
    void missingFieldDoesNotPartiallyChangeMessage() {
        var conversation = ChatConversationId.generate();
        var type = MessageTypeId.generate();
        var participant = ChatParticipantId.generate();
        var message = ChatMessage.register(conversation,type,participant,"{}","{}");
        assertThrows(NullPointerException.class,
                () -> message.update(ChatConversationId.generate(),type,participant,"[]",null));
        assertEquals(conversation,message.conversationId());
        assertEquals("{}",message.content());
        assertEquals(1,message.domainEvents().size());
        assertThrows(NullPointerException.class, () -> ChatMessage.register(conversation,type,null,"{}","{}"));
        assertThrows(NullPointerException.class, () -> ChatMessage.register(conversation,type,participant,null,"{}"));
    }
}
