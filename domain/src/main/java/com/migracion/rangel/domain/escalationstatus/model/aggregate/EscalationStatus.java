package com.migracion.rangel.domain.escalationstatus.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.escalationstatus.event.EscalationStatusRegisteredEvent;
import com.migracion.rangel.domain.escalationstatus.event.EscalationStatusUpdatedEvent;

public final class EscalationStatus extends AggregateRoot {
    private final EscalationStatusId id;
    private String nameStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private EscalationStatus(EscalationStatusId id, String nameStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(nameStatus);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static EscalationStatus register(String nameStatus) {
        var now = LocalDateTime.now();
        var aggregate = new EscalationStatus(EscalationStatusId.generate(), nameStatus, now, now);
        aggregate.recordEvent(new EscalationStatusRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static EscalationStatus restore(EscalationStatusId id, String nameStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new EscalationStatus(id, nameStatus, createdAt, updatedAt);
    }
    public void update(String nameStatus) {
        setDetails(nameStatus);
        updatedAt = LocalDateTime.now();
        recordEvent(new EscalationStatusUpdatedEvent(id, updatedAt));
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
    public EscalationStatusId id() { return id; }
    public String nameStatus() { return nameStatus; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
