package com.migracion.rangel.domain.chatescalationstatushistory;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import com.migracion.rangel.domain.chatescalationstatushistory.event.*;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
class ChatEscalationStatusHistoryTest {
    @Test void changeDateIsSuppliedAndCreationDateIsPreserved() {
        var escalation = ChatEscalationId.generate();
        var escalationstatus = EscalationStatusId.generate();
        var date = LocalDateTime.of(2020,1,2,3,4);
        var value = ChatEscalationStatusHistory.register(escalation,escalationstatus,date);
        assertEquals(date,value.changedAt());
        assertInstanceOf(ChatEscalationStatusHistoryRegisteredEvent.class,value.domainEvents().getFirst());
        var restored = ChatEscalationStatusHistory.restore(value.id(),escalation,escalationstatus,date,value.createdAt());
        assertTrue(restored.domainEvents().isEmpty());
        var next = date.plusDays(1);
        restored.update(escalation,escalationstatus,next);
        assertEquals(value.id(),restored.id());
        assertEquals(value.createdAt(),restored.createdAt());
        assertEquals(next,restored.changedAt());
        assertInstanceOf(ChatEscalationStatusHistoryUpdatedEvent.class,restored.domainEvents().getFirst());
    }
    @Test void mandatoryFieldsAreValidatedBeforeMutating() {
        var escalation=ChatEscalationId.generate();
        var escalationstatus=EscalationStatusId.generate();
        var date=LocalDateTime.of(2030,1,1,0,0);
        var value=ChatEscalationStatusHistory.restore(com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId.generate(),escalation,escalationstatus,date,date.minusDays(1));
        assertThrows(NullPointerException.class,()->value.update(ChatEscalationId.generate(),EscalationStatusId.generate(),null));
        assertEquals(escalation,value.escalationId());
        assertEquals(escalationstatus,value.escalationStatusId());
        assertEquals(date,value.changedAt());
        assertTrue(value.domainEvents().isEmpty());
        assertEquals(date.minusDays(1),value.createdAt());
        assertThrows(NullPointerException.class,()->ChatEscalationStatusHistory.register(null,escalationstatus,date));
        assertThrows(NullPointerException.class,()->ChatEscalationStatusHistory.register(escalation,null,date));
        assertThrows(NullPointerException.class,()->ChatEscalationStatusHistory.register(escalation,escalationstatus,null));
        assertThrows(NullPointerException.class,()->ChatEscalationStatusHistory.restore(value.id(),escalation,escalationstatus,date,null));
    }
}
