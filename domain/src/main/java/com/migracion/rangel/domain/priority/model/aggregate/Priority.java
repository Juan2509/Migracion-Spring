package com.migracion.rangel.domain.priority.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.priority.event.PriorityRegisteredEvent;
import com.migracion.rangel.domain.priority.event.PriorityUpdatedEvent;

public final class Priority extends AggregateRoot {
    private final PriorityId id;
    private String namePriority;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Priority(PriorityId id, String namePriority, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(namePriority);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static Priority register(String namePriority) {
        var now = LocalDateTime.now();
        var aggregate = new Priority(PriorityId.generate(), namePriority, now, now);
        aggregate.recordEvent(new PriorityRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static Priority restore(PriorityId id, String namePriority, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Priority(id, namePriority, createdAt, updatedAt);
    }
    public void update(String namePriority) {
        setDetails(namePriority);
        updatedAt = LocalDateTime.now();
        recordEvent(new PriorityUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String namePriority) {
        validateText(namePriority, 50, "namePriority");
        this.namePriority = namePriority;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public PriorityId id() { return id; }
    public String namePriority() { return namePriority; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
