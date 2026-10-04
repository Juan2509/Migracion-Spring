package com.migracion.rangel.domain.chatescalationassignment;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import com.migracion.rangel.domain.chatescalationassignment.event.*;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
class ChatEscalationAssignmentTest {
    @Test void assignmentDateIsSuppliedAndRestorationDoesNotEmitEvents() {
        var escalation = ChatEscalationId.generate();
        var professional = ProfessionalId.generate();
        var date = LocalDateTime.of(2020,1,2,3,4);
        var value = ChatEscalationAssignment.register(escalation,professional,date);
        assertEquals(date,value.assignedAt());
        assertInstanceOf(ChatEscalationAssignmentRegisteredEvent.class,value.domainEvents().getFirst());
        var restored = ChatEscalationAssignment.restore(value.id(),escalation,professional,date);
        assertTrue(restored.domainEvents().isEmpty());
        var next = date.plusDays(1);
        restored.update(escalation,professional,next);
        assertEquals(value.id(),restored.id());
        assertEquals(next,restored.assignedAt());
        assertInstanceOf(ChatEscalationAssignmentUpdatedEvent.class,restored.domainEvents().getFirst());
    }
    @Test void mandatoryFieldsAreValidatedBeforeMutating() {
        var escalation=ChatEscalationId.generate();
        var professional=ProfessionalId.generate();
        var date=LocalDateTime.of(2030,1,1,0,0);
        var value=ChatEscalationAssignment.restore(com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId.generate(),escalation,professional,date);
        assertThrows(NullPointerException.class,()->value.update(ChatEscalationId.generate(),ProfessionalId.generate(),null));
        assertEquals(escalation,value.escalationId());
        assertEquals(professional,value.professionalId());
        assertEquals(date,value.assignedAt());
        assertTrue(value.domainEvents().isEmpty());
        assertThrows(NullPointerException.class,()->ChatEscalationAssignment.register(null,professional,date));
        assertThrows(NullPointerException.class,()->ChatEscalationAssignment.register(escalation,null,date));
        assertThrows(NullPointerException.class,()->ChatEscalationAssignment.register(escalation,professional,null));
    }
}
