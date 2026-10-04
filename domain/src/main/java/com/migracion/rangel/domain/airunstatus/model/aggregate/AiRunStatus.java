package com.migracion.rangel.domain.airunstatus.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.domain.airunstatus.event.AiRunStatusRegisteredEvent;
import com.migracion.rangel.domain.airunstatus.event.AiRunStatusUpdatedEvent;

public final class AiRunStatus extends AggregateRoot {
    private final AiRunStatusId id;
    private String nameStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private AiRunStatus(AiRunStatusId id, String nameStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(nameStatus);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static AiRunStatus register(String nameStatus) {
        var now = LocalDateTime.now();
        var aggregate = new AiRunStatus(AiRunStatusId.generate(), nameStatus, now, now);
        aggregate.recordEvent(new AiRunStatusRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static AiRunStatus restore(AiRunStatusId id, String nameStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new AiRunStatus(id, nameStatus, createdAt, updatedAt);
    }
    public void update(String nameStatus) {
        setDetails(nameStatus);
        updatedAt = LocalDateTime.now();
        recordEvent(new AiRunStatusUpdatedEvent(id, updatedAt));
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
    public AiRunStatusId id() { return id; }
    public String nameStatus() { return nameStatus; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
