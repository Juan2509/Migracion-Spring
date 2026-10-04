package com.migracion.rangel.infrastructure.chatescalationassignment;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.application.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.mappers.ChatEscalationAssignmentPersistenceMapper;
class ChatEscalationAssignmentPersistenceMapperTest {
    @Test void roundTripPreservesIdsAndAssignmentTimestampWithoutEvents() {
        var value=ChatEscalationAssignment.register(ChatEscalationId.generate(),ProfessionalId.generate(),LocalDateTime.of(2000,1,2,3,4,5,123456000));
        var mapper=new ChatEscalationAssignmentPersistenceMapper();
        var entity=mapper.toJpa(value);
        assertEquals(value.escalationId().value(),entity.getEscalationId());
        assertEquals(value.professionalId().value(),entity.getProfessionalId());
        assertEquals(value.assignedAt(),entity.getAssignedAt());
        var restored=mapper.toDomain(entity);
        assertEquals(ChatEscalationAssignmentResponse.from(value),ChatEscalationAssignmentResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
        assertNull(mapper.toJpa(null));
        assertNull(mapper.toDomain(null));
    }
}
