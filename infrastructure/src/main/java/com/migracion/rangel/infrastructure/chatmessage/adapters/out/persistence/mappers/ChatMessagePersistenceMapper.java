package com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.domain.chatmessage.model.aggregate.ChatMessage;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.entity.ChatMessageJpaEntity;
public class ChatMessagePersistenceMapper {
    public ChatMessageJpaEntity toJpa(ChatMessage aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ChatMessageJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setConversationId(aggregate.conversationId().value());
        entity.setMessageTypeId(aggregate.messageTypeId().value());
        entity.setParticipantId(aggregate.participantId().value());
        entity.setContent(aggregate.content());
        entity.setMetadata(aggregate.metadata());
        entity.setCreatedAt(aggregate.createdAt());
        return entity;
    }
    public ChatMessage toDomain(ChatMessageJpaEntity entity) {
        if (entity == null) { return null; }
        return ChatMessage.restore(new ChatMessageId(entity.getId()), new ChatConversationId(entity.getConversationId()), new MessageTypeId(entity.getMessageTypeId()), new ChatParticipantId(entity.getParticipantId()), entity.getContent(), entity.getMetadata(), entity.getCreatedAt());
    }
}
