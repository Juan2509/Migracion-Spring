package com.migracion.rangel.domain.chatescalation;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatescalation.model.aggregate.ChatEscalation;
import com.migracion.rangel.domain.chatescalation.event.*;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
class ChatEscalationTest {
    @Test
    void auditIdentityEventsAndLongReasonFollowSchema() {
        var conversation = ChatConversationId.generate();
        var status = EscalationStatusId.generate();
        var original = ChatEscalation.register(conversation,"r".repeat(10000),status,false);
        assertEquals(10000,original.reason().length());
        assertInstanceOf(ChatEscalationRegisteredEvent.class,original.domainEvents().getFirst());
        var created = LocalDateTime.of(2020,1,1,0,0);
        var restored = ChatEscalation.restore(original.id(),conversation,original.reason(),status,false,created);
        assertTrue(restored.domainEvents().isEmpty());
        var nextStatus = EscalationStatusId.generate();
        restored.update(conversation,"Nuevo motivo",nextStatus,true);
        assertEquals(original.id(),restored.id());
        assertEquals(created,restored.createdAt());
        assertEquals(nextStatus,restored.statusId());
        assertTrue(restored.fromAi());
        assertInstanceOf(ChatEscalationUpdatedEvent.class,restored.domainEvents().getFirst());
    }
    @Test
    void requiredFieldsAndInvalidUpdatePreserveState() {
        var conversation = ChatConversationId.generate();
        var status = EscalationStatusId.generate();
        var escalation = ChatEscalation.register(conversation,"Original",status,false);
        assertThrows(NullPointerException.class, () -> escalation.update(ChatConversationId.generate(),"Nuevo",status,null));
        assertEquals(conversation,escalation.conversationId());
        assertEquals("Original",escalation.reason());
        assertFalse(escalation.fromAi());
        assertEquals(1,escalation.domainEvents().size());
        assertThrows(NullPointerException.class, () -> ChatEscalation.register(null,"r",status,false));
        assertThrows(NullPointerException.class, () -> ChatEscalation.register(conversation,null,status,false));
        assertThrows(NullPointerException.class, () -> ChatEscalation.register(conversation,"r",null,false));
        assertThrows(NullPointerException.class, () -> ChatEscalation.register(conversation,"r",status,null));
    }
}
