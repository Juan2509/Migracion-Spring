package com.migracion.rangel.domain.professionaltype.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.professionaltype.event.ProfessionalTypeRegisteredEvent;
import com.migracion.rangel.domain.professionaltype.event.ProfessionalTypeUpdatedEvent;

public final class ProfessionalType extends AggregateRoot {
    private final ProfessionalTypeId id;
    private String name;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ProfessionalType(ProfessionalTypeId id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(name);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static ProfessionalType register(String name) {
        var now = LocalDateTime.now();
        var aggregate = new ProfessionalType(ProfessionalTypeId.generate(), name, now, now);
        aggregate.recordEvent(new ProfessionalTypeRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ProfessionalType restore(ProfessionalTypeId id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ProfessionalType(id, name, createdAt, updatedAt);
    }
    public void update(String name) {
        setDetails(name);
        updatedAt = LocalDateTime.now();
        recordEvent(new ProfessionalTypeUpdatedEvent(id, updatedAt));
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
    public ProfessionalTypeId id() { return id; }
    public String name() { return name; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
