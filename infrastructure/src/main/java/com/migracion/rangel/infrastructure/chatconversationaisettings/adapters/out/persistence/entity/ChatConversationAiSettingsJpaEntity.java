package com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.entity;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity
@Table(name = "chat_conversation_ai_settings")
public class ChatConversationAiSettingsJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "conversation_id", nullable = false)
    private UUID conversationId;
    @Column(name = "ai_enabled", nullable = false)
    private Boolean aiEnabled;
    @Column(name = "default_model_id", nullable = false)
    private UUID defaultModelId;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    public ChatConversationAiSettingsJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getConversationId() { return conversationId; }
    public void setConversationId(UUID conversationId) { this.conversationId = conversationId; }
    public Boolean getAiEnabled() { return aiEnabled; }
    public void setAiEnabled(Boolean aiEnabled) { this.aiEnabled = aiEnabled; }
    public UUID getDefaultModelId() { return defaultModelId; }
    public void setDefaultModelId(UUID defaultModelId) { this.defaultModelId = defaultModelId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
