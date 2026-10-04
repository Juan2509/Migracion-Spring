package com.migracion.rangel.domain.conversationstatus.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.conversationstatus.event.ConversationStatusRegisteredEvent;
import com.migracion.rangel.domain.conversationstatus.event.ConversationStatusUpdatedEvent;

public final class ConversationStatus extends AggregateRoot {
    private final ConversationStatusId id;
    private String nameStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ConversationStatus(ConversationStatusId id, String nameStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(nameStatus);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static ConversationStatus register(String nameStatus) {
        var now = LocalDateTime.now();
        var aggregate = new ConversationStatus(ConversationStatusId.generate(), nameStatus, now, now);
        aggregate.recordEvent(new ConversationStatusRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ConversationStatus restore(ConversationStatusId id, String nameStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ConversationStatus(id, nameStatus, createdAt, updatedAt);
    }
    public void update(String nameStatus) {
        setDetails(nameStatus);
        updatedAt = LocalDateTime.now();
        recordEvent(new ConversationStatusUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String nameStatus) {
        validateText(nameStatus, 50, "nameStatus");
        this.nameStatus = nameStatus;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public ConversationStatusId id() { return id; }
    public String nameStatus() { return nameStatus; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
