package com.migracion.rangel.domain.chatairun;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatairun.model.aggregate.ChatAiRun;
import com.migracion.rangel.domain.chatairun.event.*;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
class ChatAiRunTest {
    @Test
    void restorationAndUpdatePreserveAuditAndIdentity() {
        var conversation = ChatConversationId.generate();
        var message = ChatMessageId.generate();
        var model = AiModelId.generate();
        var status = AiRunStatusId.generate();
        var original = ChatAiRun.register(conversation,message,model,status);
        assertInstanceOf(ChatAiRunRegisteredEvent.class,original.domainEvents().getFirst());
        var created = LocalDateTime.of(2020,1,1,0,0);
        var restored = ChatAiRun.restore(original.id(),conversation,message,model,status,created,created);
        assertTrue(restored.domainEvents().isEmpty());
        var nextStatus = AiRunStatusId.generate();
        restored.update(conversation,message,model,nextStatus);
        assertEquals(nextStatus,restored.aiRunStatusId());
        assertEquals(original.id(),restored.id());
        assertEquals(created,restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(ChatAiRunUpdatedEvent.class,restored.domainEvents().getFirst());
    }
    @Test
    void allReferencesAreRequiredAndInvalidUpdateIsAtomic() {
        var conversation = ChatConversationId.generate();
        var message = ChatMessageId.generate();
        var model = AiModelId.generate();
        var status = AiRunStatusId.generate();
        var run = ChatAiRun.register(conversation,message,model,status);
        var updatedAt = run.updatedAt();
        assertThrows(NullPointerException.class, () -> run.update(ChatConversationId.generate(),message,model,null));
        assertEquals(conversation,run.conversationId());
        assertEquals(status,run.aiRunStatusId());
        assertEquals(updatedAt,run.updatedAt());
        assertEquals(1,run.domainEvents().size());
        assertThrows(NullPointerException.class, () -> ChatAiRun.register(null,message,model,status));
        assertThrows(NullPointerException.class, () -> ChatAiRun.register(conversation,null,model,status));
        assertThrows(NullPointerException.class, () -> ChatAiRun.register(conversation,message,null,status));
        assertThrows(NullPointerException.class, () -> ChatAiRun.register(conversation,message,model,null));
    }
}
