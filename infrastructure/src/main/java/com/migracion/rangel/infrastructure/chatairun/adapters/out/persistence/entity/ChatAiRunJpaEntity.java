package com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.entity;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity
@Table(name = "chat_ai_runs")
public class ChatAiRunJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "conversation_id", nullable = false)
    private UUID conversationId;
    @Column(name = "message_id", nullable = false)
    private UUID messageId;
    @Column(name = "model_id", nullable = false)
    private UUID modelId;
    @Column(name = "ai_run_status_id", nullable = false)
    private UUID aiRunStatusId;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    public ChatAiRunJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getConversationId() { return conversationId; }
    public void setConversationId(UUID conversationId) { this.conversationId = conversationId; }
    public UUID getMessageId() { return messageId; }
    public void setMessageId(UUID messageId) { this.messageId = messageId; }
    public UUID getModelId() { return modelId; }
    public void setModelId(UUID modelId) { this.modelId = modelId; }
    public UUID getAiRunStatusId() { return aiRunStatusId; }
    public void setAiRunStatusId(UUID aiRunStatusId) { this.aiRunStatusId = aiRunStatusId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
