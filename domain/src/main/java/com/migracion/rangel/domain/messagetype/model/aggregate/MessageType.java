package com.migracion.rangel.domain.messagetype.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.messagetype.event.MessageTypeRegisteredEvent;
import com.migracion.rangel.domain.messagetype.event.MessageTypeUpdatedEvent;

public final class MessageType extends AggregateRoot {
    private final MessageTypeId id;
    private String nameType;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private MessageType(MessageTypeId id, String nameType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(nameType);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static MessageType register(String nameType) {
        var now = LocalDateTime.now();
        var aggregate = new MessageType(MessageTypeId.generate(), nameType, now, now);
        aggregate.recordEvent(new MessageTypeRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static MessageType restore(MessageTypeId id, String nameType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new MessageType(id, nameType, createdAt, updatedAt);
    }
    public void update(String nameType) {
        setDetails(nameType);
        updatedAt = LocalDateTime.now();
        recordEvent(new MessageTypeUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String nameType) {
        validateText(nameType, 50, "nameType");
        this.nameType = nameType;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public MessageTypeId id() { return id; }
    public String nameType() { return nameType; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
