package com.migracion.rangel.domain.assessmenttype.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import com.migracion.rangel.domain.assessmenttype.event.AssessmentTypeRegisteredEvent;
import com.migracion.rangel.domain.assessmenttype.event.AssessmentTypeUpdatedEvent;

public final class AssessmentType extends AggregateRoot {
    private final AssessmentTypeId id;
    private String code;
    private String name;
    private Boolean active;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private AssessmentType(AssessmentTypeId id, String code, String name, Boolean active, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(code, name, active, description);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static AssessmentType register(String code, String name, Boolean active, String description) {
        var now = LocalDateTime.now();
        var aggregate = new AssessmentType(AssessmentTypeId.generate(), code, name, active, description, now, now);
        aggregate.recordEvent(new AssessmentTypeRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static AssessmentType restore(AssessmentTypeId id, String code, String name, Boolean active, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new AssessmentType(id, code, name, active, description, createdAt, updatedAt);
    }
    public void update(String code, String name, Boolean active, String description) {
        setDetails(code, name, active, description);
        updatedAt = LocalDateTime.now();
        recordEvent(new AssessmentTypeUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String code, String name, Boolean active, String description) {
        validateText(code, 20, "code");
        validateText(name, 50, "name");
        Objects.requireNonNull(active, "active es obligatorio");
        Objects.requireNonNull(description, "description es obligatorio");
        this.description = description;
        this.code = code;
        this.name = name;
        this.active = active;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public AssessmentTypeId id() { return id; }
    public String code() { return code; }
    public String name() { return name; }
    public String description() { return description; }
    public Boolean active() { return active; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}

