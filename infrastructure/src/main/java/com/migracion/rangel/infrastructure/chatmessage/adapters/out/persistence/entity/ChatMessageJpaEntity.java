package com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.entity;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
@Entity
@Table(name = "chat_messages")
public class ChatMessageJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "conversation_id", nullable = false)
    private UUID conversationId;
    @Column(name = "message_type_id", nullable = false)
    private UUID messageTypeId;
    @Column(name = "participant_id", nullable = false)
    private UUID participantId;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content", nullable = false, columnDefinition = "JSONB")
    private String content;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "JSONB")
    private String metadata;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    public ChatMessageJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getConversationId() { return conversationId; }
    public void setConversationId(UUID conversationId) { this.conversationId = conversationId; }
    public UUID getMessageTypeId() { return messageTypeId; }
    public void setMessageTypeId(UUID messageTypeId) { this.messageTypeId = messageTypeId; }
    public UUID getParticipantId() { return participantId; }
    public void setParticipantId(UUID participantId) { this.participantId = participantId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
