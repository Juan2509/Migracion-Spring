package com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.entity.ChatConversationJpaEntity;
public class ChatConversationPersistenceMapper {
    public ChatConversationJpaEntity toJpa(ChatConversation aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ChatConversationJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setConversationStatusId(aggregate.conversationStatusId().value());
        entity.setPriorityId(aggregate.priorityId().value());
        entity.setLastMessageAt(aggregate.lastMessageAt());
        entity.setClosed(aggregate.closed());
        entity.setClosedAt(aggregate.closedAt());
        entity.setClosedBy(aggregate.closedBy());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ChatConversation toDomain(ChatConversationJpaEntity entity) {
        if (entity == null) { return null; }
        return ChatConversation.restore(new ChatConversationId(entity.getId()), new ConversationStatusId(entity.getConversationStatusId()), new PriorityId(entity.getPriorityId()), entity.getLastMessageAt(), entity.getClosed(), entity.getClosedAt(), entity.getClosedBy(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
