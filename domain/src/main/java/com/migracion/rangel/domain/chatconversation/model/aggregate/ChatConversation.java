package com.migracion.rangel.domain.chatconversation.model.aggregate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatconversation.event.*;
public final class ChatConversation extends AggregateRoot {
    private final ChatConversationId id;
    private ConversationStatusId conversationStatusId;
    private PriorityId priorityId;
    private LocalDateTime lastMessageAt;
    private Boolean closed;
    private LocalDateTime closedAt;
    private UUID closedBy;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ChatConversation(ChatConversationId id, ConversationStatusId conversationStatusId, PriorityId priorityId, LocalDateTime lastMessageAt, Boolean closed, LocalDateTime closedAt, UUID closedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        setDetails(conversationStatusId, priorityId, lastMessageAt, closed, closedAt, closedBy);
    }
    public static ChatConversation register(ConversationStatusId conversationStatusId, PriorityId priorityId, LocalDateTime lastMessageAt, Boolean closed, LocalDateTime closedAt, UUID closedBy) {
        var now = LocalDateTime.now();
        var aggregate = new ChatConversation(ChatConversationId.generate(), conversationStatusId, priorityId, lastMessageAt, closed, closedAt, closedBy, now, now);
        aggregate.recordEvent(new ChatConversationRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ChatConversation restore(ChatConversationId id, ConversationStatusId conversationStatusId, PriorityId priorityId, LocalDateTime lastMessageAt, Boolean closed, LocalDateTime closedAt, UUID closedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ChatConversation(id, conversationStatusId, priorityId, lastMessageAt, closed, closedAt, closedBy, createdAt, updatedAt);
    }
    public void update(ConversationStatusId conversationStatusId, PriorityId priorityId, LocalDateTime lastMessageAt, Boolean closed, LocalDateTime closedAt, UUID closedBy) {
        setDetails(conversationStatusId, priorityId, lastMessageAt, closed, closedAt, closedBy);
        updatedAt = LocalDateTime.now();
        recordEvent(new ChatConversationUpdatedEvent(id, updatedAt));
    }
    private void setDetails(ConversationStatusId conversationStatusId, PriorityId priorityId, LocalDateTime lastMessageAt, Boolean closed, LocalDateTime closedAt, UUID closedBy) {
        Objects.requireNonNull(conversationStatusId, "conversationStatusId es obligatorio");
        Objects.requireNonNull(priorityId, "priorityId es obligatorio");
        this.conversationStatusId = conversationStatusId;
        this.priorityId = priorityId;
        this.lastMessageAt = lastMessageAt;
        this.closed = closed;
        this.closedAt = closedAt;
        this.closedBy = closedBy;
    }
    public ChatConversationId id() { return id; }
    public ConversationStatusId conversationStatusId() { return conversationStatusId; }
    public PriorityId priorityId() { return priorityId; }
    public LocalDateTime lastMessageAt() { return lastMessageAt; }
    public Boolean closed() { return closed; }
    public LocalDateTime closedAt() { return closedAt; }
    public UUID closedBy() { return closedBy; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
