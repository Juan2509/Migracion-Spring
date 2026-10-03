package com.migracion.rangel.domain.study.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.study.event.StudyRegisteredEvent;
import com.migracion.rangel.domain.study.event.StudyUpdatedEvent;

public final class Study extends AggregateRoot {
    private final StudyId id;
    private String name;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Study(StudyId id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(name);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static Study register(String name) {
        var now = LocalDateTime.now();
        var aggregate = new Study(StudyId.generate(), name, now, now);
        aggregate.recordEvent(new StudyRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static Study restore(StudyId id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Study(id, name, createdAt, updatedAt);
    }
    public void update(String name) {
        setDetails(name);
        updatedAt = LocalDateTime.now();
        recordEvent(new StudyUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String name) {
        validateText(name, 40, "name");
        this.name = name;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public StudyId id() { return id; }
    public String name() { return name; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
