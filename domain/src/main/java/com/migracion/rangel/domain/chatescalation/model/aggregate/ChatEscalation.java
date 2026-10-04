package com.migracion.rangel.domain.chatescalation.model.aggregate;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.chatescalation.event.*;
public final class ChatEscalation extends AggregateRoot {
    private final ChatEscalationId id;
    private ChatConversationId conversationId;
    private String reason;
    private EscalationStatusId statusId;
    private Boolean fromAi;

    private final LocalDateTime createdAt;
    private ChatEscalation(ChatEscalationId id, ChatConversationId conversationId, String reason, EscalationStatusId statusId, Boolean fromAi, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id);
        this.createdAt = Objects.requireNonNull(createdAt);
        setDetails(conversationId, reason, statusId, fromAi);
    }
    public static ChatEscalation register(ChatConversationId conversationId, String reason, EscalationStatusId statusId, Boolean fromAi) {
        var now = LocalDateTime.now();
        var aggregate = new ChatEscalation(ChatEscalationId.generate(), conversationId, reason, statusId, fromAi, now);
        aggregate.recordEvent(new ChatEscalationRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ChatEscalation restore(ChatEscalationId id, ChatConversationId conversationId, String reason, EscalationStatusId statusId, Boolean fromAi, LocalDateTime createdAt) {
        return new ChatEscalation(id, conversationId, reason, statusId, fromAi, createdAt);
    }
    public void update(ChatConversationId conversationId, String reason, EscalationStatusId statusId, Boolean fromAi) {
        setDetails(conversationId, reason, statusId, fromAi);
        recordEvent(new ChatEscalationUpdatedEvent(id, LocalDateTime.now()));
    }
    private void setDetails(ChatConversationId conversationId, String reason, EscalationStatusId statusId, Boolean fromAi) {
        Objects.requireNonNull(conversationId, "conversationId es obligatorio");
        Objects.requireNonNull(reason, "reason es obligatorio");
        Objects.requireNonNull(statusId, "statusId es obligatorio");
        Objects.requireNonNull(fromAi, "fromAi es obligatorio");
        this.conversationId = conversationId;
        this.reason = reason;
        this.statusId = statusId;
        this.fromAi = fromAi;

    }
    public ChatEscalationId id() { return id; }
    public ChatConversationId conversationId() { return conversationId; }
    public String reason() { return reason; }
    public EscalationStatusId statusId() { return statusId; }
    public Boolean fromAi() { return fromAi; }
    public LocalDateTime createdAt() { return createdAt; }
}
