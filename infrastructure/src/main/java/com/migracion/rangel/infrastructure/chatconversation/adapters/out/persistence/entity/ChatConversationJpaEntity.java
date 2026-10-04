package com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.entity;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity
@Table(name = "chat_conversations")
public class ChatConversationJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "conversation_status_id", nullable = false)
    private UUID conversationStatusId;
    @Column(name = "priority_id", nullable = false)
    private UUID priorityId;
    @Column(name = "last_message_at", nullable = true)
    private LocalDateTime lastMessageAt;
    @Column(name = "closed", nullable = true)
    private Boolean closed;
    @Column(name = "closed_at", nullable = true)
    private LocalDateTime closedAt;
    @Column(name = "closed_by", nullable = true)
    private UUID closedBy;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    public ChatConversationJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getConversationStatusId() { return conversationStatusId; }
    public void setConversationStatusId(UUID conversationStatusId) { this.conversationStatusId = conversationStatusId; }
    public UUID getPriorityId() { return priorityId; }
    public void setPriorityId(UUID priorityId) { this.priorityId = priorityId; }
    public LocalDateTime getLastMessageAt() { return lastMessageAt; }
    public void setLastMessageAt(LocalDateTime lastMessageAt) { this.lastMessageAt = lastMessageAt; }
    public Boolean getClosed() { return closed; }
    public void setClosed(Boolean closed) { this.closed = closed; }
    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }
    public UUID getClosedBy() { return closedBy; }
    public void setClosedBy(UUID closedBy) { this.closedBy = closedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
