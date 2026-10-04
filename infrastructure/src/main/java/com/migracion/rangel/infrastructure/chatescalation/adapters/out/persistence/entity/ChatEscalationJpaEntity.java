package com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.entity;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity
@Table(name = "chat_escalations")
public class ChatEscalationJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "conversation_id", nullable = false)
    private UUID conversationId;
    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    private String reason;
    @Column(name = "status_id", nullable = false)
    private UUID statusId;
    @Column(name = "from_ai", nullable = false)
    private Boolean fromAi;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    public ChatEscalationJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getConversationId() { return conversationId; }
    public void setConversationId(UUID conversationId) { this.conversationId = conversationId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public UUID getStatusId() { return statusId; }
    public void setStatusId(UUID statusId) { this.statusId = statusId; }
    public Boolean getFromAi() { return fromAi; }
    public void setFromAi(Boolean fromAi) { this.fromAi = fromAi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
