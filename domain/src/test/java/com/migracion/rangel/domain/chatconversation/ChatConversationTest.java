package com.migracion.rangel.domain.chatconversation;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.chatconversation.event.*;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
class ChatConversationTest {
    @Test
    void nullableFieldsAuditAndEventsFollowSchema() {
        var status = ConversationStatusId.generate();
        var priority = PriorityId.generate();
        var original = ChatConversation.register(status, priority, null, null, null, null);
        assertNull(original.closed());
        assertInstanceOf(ChatConversationRegisteredEvent.class, original.domainEvents().getFirst());
        var created = LocalDateTime.of(2020,1,1,0,0);
        var restored = ChatConversation.restore(original.id(), status, priority, null, null, null, null, created, created);
        assertTrue(restored.domainEvents().isEmpty());
        var now = LocalDateTime.now();
        var closedBy = UUID.randomUUID();
        restored.update(status, priority, now, true, now, closedBy);
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertEquals(now, restored.lastMessageAt());
        assertEquals(now, restored.closedAt());
        assertEquals(closedBy, restored.closedBy());
        assertInstanceOf(ChatConversationUpdatedEvent.class, restored.domainEvents().getFirst());
        restored.update(status, priority, null, null, null, null);
        assertNull(restored.closedBy());
        assertNull(restored.closedAt());
    }
    @Test
    void requiredReferenceFailureDoesNotPartiallyUpdate() {
        var status = ConversationStatusId.generate();
        var priority = PriorityId.generate();
        var aggregate = ChatConversation.register(status, priority, null, null, null, null);
        var updatedAt = aggregate.updatedAt();
        assertThrows(NullPointerException.class,
                () -> aggregate.update(ConversationStatusId.generate(), null, LocalDateTime.now(), true, null, UUID.randomUUID()));
        assertEquals(status, aggregate.conversationStatusId());
        assertEquals(priority, aggregate.priorityId());
        assertNull(aggregate.closed());
        assertEquals(updatedAt, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> ChatConversation.register(null, priority, null, null, null, null));
    }
}
