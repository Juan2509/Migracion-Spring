package com.migracion.rangel.domain.chatairun.model.aggregate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.domain.chatairun.event.*;
public final class ChatAiRun extends AggregateRoot {
    private final ChatAiRunId id;
    private ChatConversationId conversationId;
    private ChatMessageId messageId;
    private AiModelId modelId;
    private AiRunStatusId aiRunStatusId;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ChatAiRun(ChatAiRunId id, ChatConversationId conversationId, ChatMessageId messageId, AiModelId modelId, AiRunStatusId aiRunStatusId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        setDetails(conversationId, messageId, modelId, aiRunStatusId);
    }
    public static ChatAiRun register(ChatConversationId conversationId, ChatMessageId messageId, AiModelId modelId, AiRunStatusId aiRunStatusId) {
        var now = LocalDateTime.now();
        var aggregate = new ChatAiRun(ChatAiRunId.generate(), conversationId, messageId, modelId, aiRunStatusId, now, now);
        aggregate.recordEvent(new ChatAiRunRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ChatAiRun restore(ChatAiRunId id, ChatConversationId conversationId, ChatMessageId messageId, AiModelId modelId, AiRunStatusId aiRunStatusId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ChatAiRun(id, conversationId, messageId, modelId, aiRunStatusId, createdAt, updatedAt);
    }
    public void update(ChatConversationId conversationId, ChatMessageId messageId, AiModelId modelId, AiRunStatusId aiRunStatusId) {
        setDetails(conversationId, messageId, modelId, aiRunStatusId);
        updatedAt = LocalDateTime.now();
        recordEvent(new ChatAiRunUpdatedEvent(id, updatedAt));
    }
    private void setDetails(ChatConversationId conversationId, ChatMessageId messageId, AiModelId modelId, AiRunStatusId aiRunStatusId) {
        Objects.requireNonNull(conversationId, "conversationId es obligatorio");
        Objects.requireNonNull(messageId, "messageId es obligatorio");
        Objects.requireNonNull(modelId, "modelId es obligatorio");
        Objects.requireNonNull(aiRunStatusId, "aiRunStatusId es obligatorio");
        this.conversationId = conversationId;
        this.messageId = messageId;
        this.modelId = modelId;
        this.aiRunStatusId = aiRunStatusId;
    }
    public ChatAiRunId id() { return id; }
    public ChatConversationId conversationId() { return conversationId; }
    public ChatMessageId messageId() { return messageId; }
    public AiModelId modelId() { return modelId; }
    public AiRunStatusId aiRunStatusId() { return aiRunStatusId; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
