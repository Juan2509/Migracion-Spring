package com.migracion.rangel.domain.chatescalationstatushistory.model.aggregate;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.migracion.rangel.domain.chatescalationstatushistory.event.*;
public final class ChatEscalationStatusHistory extends AggregateRoot {
    private final ChatEscalationStatusHistoryId id;
    private ChatEscalationId escalationId;
    private EscalationStatusId escalationStatusId;
    private LocalDateTime changedAt;
    private final LocalDateTime createdAt;
    private ChatEscalationStatusHistory(ChatEscalationStatusHistoryId id, ChatEscalationId escalationId, EscalationStatusId escalationStatusId, LocalDateTime changedAt, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        setDetails(escalationId, escalationStatusId, changedAt);
    }
    public static ChatEscalationStatusHistory register(ChatEscalationId escalationId, EscalationStatusId escalationStatusId, LocalDateTime changedAt) {
        var now = LocalDateTime.now();
        var value = new ChatEscalationStatusHistory(ChatEscalationStatusHistoryId.generate(), escalationId, escalationStatusId, changedAt, now);
        value.recordEvent(new ChatEscalationStatusHistoryRegisteredEvent(value.id, now));
        return value;
    }
    public static ChatEscalationStatusHistory restore(ChatEscalationStatusHistoryId id, ChatEscalationId escalationId, EscalationStatusId escalationStatusId, LocalDateTime changedAt, LocalDateTime createdAt) {
        return new ChatEscalationStatusHistory(id, escalationId, escalationStatusId, changedAt, createdAt);
    }
    public void update(ChatEscalationId escalationId, EscalationStatusId escalationStatusId, LocalDateTime changedAt) {
        setDetails(escalationId, escalationStatusId, changedAt);
        recordEvent(new ChatEscalationStatusHistoryUpdatedEvent(id, LocalDateTime.now()));
    }
    private void setDetails(ChatEscalationId escalationId, EscalationStatusId escalationStatusId, LocalDateTime changedAt) {
        Objects.requireNonNull(escalationId, "escalationId es obligatorio");
        Objects.requireNonNull(escalationStatusId, "escalationStatusId es obligatorio");
        Objects.requireNonNull(changedAt, "changedAt es obligatorio");
        this.escalationId = escalationId;
        this.escalationStatusId = escalationStatusId;
        this.changedAt = changedAt;
    }
    public ChatEscalationStatusHistoryId id() { return id; }
    public ChatEscalationId escalationId() { return escalationId; }
    public EscalationStatusId escalationStatusId() { return escalationStatusId; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime changedAt() { return changedAt; }
}
