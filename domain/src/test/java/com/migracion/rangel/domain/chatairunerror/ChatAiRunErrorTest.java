package com.migracion.rangel.domain.chatairunerror;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatairunerror.model.aggregate.ChatAiRunError;
import com.migracion.rangel.domain.chatairunerror.event.*;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
class ChatAiRunErrorTest {
    @Test
    void restoreAndUpdatePreserveCreationDateAndIdentity() {
        var run = ChatAiRunId.generate();
        var original = ChatAiRunError.register(run,"Error","E1","P1");
        assertInstanceOf(ChatAiRunErrorRegisteredEvent.class,original.domainEvents().getFirst());
        var created = LocalDateTime.of(2020,1,1,0,0);
        var restored = ChatAiRunError.restore(original.id(),run,"Error","E1","P1",created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update(run,"Otro error","E2","P2");
        assertEquals(original.id(),restored.id());
        assertEquals(created,restored.createdAt());
        assertEquals("Otro error",restored.errorMessage());
        assertEquals("E2",restored.errorCode());
        assertEquals("P2",restored.providerErrorId());
        assertInstanceOf(ChatAiRunErrorUpdatedEvent.class,restored.domainEvents().getFirst());
    }
    @Test
    void textHasNoArtificialLimitAndIdentifiersFollowTheirLengths() {
        var run = ChatAiRunId.generate();
        var error = ChatAiRunError.register(run,"x".repeat(10000),"😀".repeat(80),"p".repeat(120));
        assertEquals(10000,error.errorMessage().length());
        assertThrows(IllegalArgumentException.class, () -> ChatAiRunError.register(run,"Error","e".repeat(81),"p"));
        assertThrows(IllegalArgumentException.class, () -> ChatAiRunError.register(run,"Error","e","p".repeat(121)));
    }
    @Test
    void invalidUpdateDoesNotPartiallyChangeError() {
        var run = ChatAiRunId.generate();
        var error = ChatAiRunError.register(run,"Original","E1","P1");
        assertThrows(IllegalArgumentException.class, () -> error.update(ChatAiRunId.generate(),"Nuevo","E2","p".repeat(121)));
        assertEquals(run,error.aiRunId());
        assertEquals("Original",error.errorMessage());
        assertEquals("E1",error.errorCode());
        assertEquals(1,error.domainEvents().size());
        assertThrows(NullPointerException.class, () -> ChatAiRunError.register(null,"e","c","p"));
        assertThrows(NullPointerException.class, () -> ChatAiRunError.register(run,null,"c","p"));
        assertThrows(NullPointerException.class, () -> ChatAiRunError.register(run,"e",null,"p"));
        assertThrows(NullPointerException.class, () -> ChatAiRunError.register(run,"e","c",null));
    }
}
