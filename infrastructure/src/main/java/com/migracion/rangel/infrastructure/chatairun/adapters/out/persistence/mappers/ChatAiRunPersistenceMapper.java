package com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.domain.chatairun.model.aggregate.ChatAiRun;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.entity.ChatAiRunJpaEntity;
public class ChatAiRunPersistenceMapper {
    public ChatAiRunJpaEntity toJpa(ChatAiRun aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ChatAiRunJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setConversationId(aggregate.conversationId().value());
        entity.setMessageId(aggregate.messageId().value());
        entity.setModelId(aggregate.modelId().value());
        entity.setAiRunStatusId(aggregate.aiRunStatusId().value());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ChatAiRun toDomain(ChatAiRunJpaEntity entity) {
        if (entity == null) { return null; }
        return ChatAiRun.restore(new ChatAiRunId(entity.getId()), new ChatConversationId(entity.getConversationId()), new ChatMessageId(entity.getMessageId()), new AiModelId(entity.getModelId()), new AiRunStatusId(entity.getAiRunStatusId()), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
