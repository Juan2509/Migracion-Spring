package com.migracion.rangel.infrastructure.chatescalationstatushistory;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.application.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.out.persistence.mappers.ChatEscalationStatusHistoryPersistenceMapper;
class ChatEscalationStatusHistoryPersistenceMapperTest {
    @Test void roundTripPreservesIdsAndAssignmentTimestampWithoutEvents() {
        var value=ChatEscalationStatusHistory.register(ChatEscalationId.generate(),EscalationStatusId.generate(),LocalDateTime.of(2000,1,2,3,4,5,123456000));
        var mapper=new ChatEscalationStatusHistoryPersistenceMapper();
        var entity=mapper.toJpa(value);
        assertEquals(value.escalationId().value(),entity.getEscalationId());
        assertEquals(value.escalationStatusId().value(),entity.getEscalationStatusId());
        assertEquals(value.changedAt(),entity.getChangedAt());
        assertEquals(value.createdAt(),entity.getCreatedAt());
        var restored=mapper.toDomain(entity);
        assertEquals(ChatEscalationStatusHistoryResponse.from(value),ChatEscalationStatusHistoryResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
        assertNull(mapper.toJpa(null));
        assertNull(mapper.toDomain(null));
    }
}
