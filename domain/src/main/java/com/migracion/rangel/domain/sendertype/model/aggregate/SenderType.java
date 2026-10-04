package com.migracion.rangel.domain.sendertype.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.domain.sendertype.event.SenderTypeRegisteredEvent;
import com.migracion.rangel.domain.sendertype.event.SenderTypeUpdatedEvent;

public final class SenderType extends AggregateRoot {
    private final SenderTypeId id;
    private String nameType;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private SenderType(SenderTypeId id, String nameType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(nameType);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static SenderType register(String nameType) {
        var now = LocalDateTime.now();
        var aggregate = new SenderType(SenderTypeId.generate(), nameType, now, now);
        aggregate.recordEvent(new SenderTypeRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static SenderType restore(SenderTypeId id, String nameType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new SenderType(id, nameType, createdAt, updatedAt);
    }
    public void update(String nameType) {
        setDetails(nameType);
        updatedAt = LocalDateTime.now();
        recordEvent(new SenderTypeUpdatedEvent(id, updatedAt));
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
    public SenderTypeId id() { return id; }
    public String nameType() { return nameType; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
