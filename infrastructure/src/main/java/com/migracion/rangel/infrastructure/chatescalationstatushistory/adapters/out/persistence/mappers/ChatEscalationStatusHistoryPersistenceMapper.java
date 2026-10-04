package com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.out.persistence.entity.ChatEscalationStatusHistoryJpaEntity;
public class ChatEscalationStatusHistoryPersistenceMapper {
    public ChatEscalationStatusHistoryJpaEntity toJpa(ChatEscalationStatusHistory value) {
        if (value == null) { return null; }
        var entity = new ChatEscalationStatusHistoryJpaEntity();
        entity.setId(value.id().value());
        entity.setEscalationId(value.escalationId().value());
        entity.setEscalationStatusId(value.escalationStatusId().value());
        entity.setChangedAt(value.changedAt());
        entity.setCreatedAt(value.createdAt());
        return entity;
    }
    public ChatEscalationStatusHistory toDomain(ChatEscalationStatusHistoryJpaEntity entity) {
        if (entity == null) { return null; }
        return ChatEscalationStatusHistory.restore(new ChatEscalationStatusHistoryId(entity.getId()), new ChatEscalationId(entity.getEscalationId()), new EscalationStatusId(entity.getEscalationStatusId()), entity.getChangedAt(), entity.getCreatedAt());
    }
}
