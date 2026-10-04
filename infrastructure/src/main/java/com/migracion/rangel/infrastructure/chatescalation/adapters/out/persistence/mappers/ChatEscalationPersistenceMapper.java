package com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatescalation.model.aggregate.ChatEscalation;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.entity.ChatEscalationJpaEntity;
public class ChatEscalationPersistenceMapper {
    public ChatEscalationJpaEntity toJpa(ChatEscalation aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ChatEscalationJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setConversationId(aggregate.conversationId().value());
        entity.setReason(aggregate.reason());
        entity.setStatusId(aggregate.statusId().value());
        entity.setFromAi(aggregate.fromAi());
        entity.setCreatedAt(aggregate.createdAt());
        return entity;
    }
    public ChatEscalation toDomain(ChatEscalationJpaEntity entity) {
        if (entity == null) { return null; }
        return ChatEscalation.restore(new ChatEscalationId(entity.getId()), new ChatConversationId(entity.getConversationId()), entity.getReason(), new EscalationStatusId(entity.getStatusId()), entity.getFromAi(), entity.getCreatedAt());
    }
}
