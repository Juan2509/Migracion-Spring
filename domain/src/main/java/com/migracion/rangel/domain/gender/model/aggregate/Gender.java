package com.migracion.rangel.domain.gender.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.gender.event.GenderRegisteredEvent;
import com.migracion.rangel.domain.gender.event.GenderUpdatedEvent;

public final class Gender extends AggregateRoot {
    private final GenderId id;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Gender(GenderId id, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(description);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static Gender register(String description) {
        var now = LocalDateTime.now();
        var aggregate = new Gender(GenderId.generate(), description, now, now);
        aggregate.recordEvent(new GenderRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static Gender restore(GenderId id, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Gender(id, description, createdAt, updatedAt);
    }
    public void update(String description) {
        setDetails(description);
        updatedAt = LocalDateTime.now();
        recordEvent(new GenderUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String description) {
        validateText(description, 50, "description");
        this.description = description;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public GenderId id() { return id; }
    public String description() { return description; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
