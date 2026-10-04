package com.migracion.rangel.domain.consenttype.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
import com.migracion.rangel.domain.consenttype.event.ConsentTypeRegisteredEvent;
import com.migracion.rangel.domain.consenttype.event.ConsentTypeUpdatedEvent;

public final class ConsentType extends AggregateRoot {
    private final ConsentTypeId id;
    private String code;
    private String name;
    private Boolean active;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ConsentType(ConsentTypeId id, String code, String name, Boolean active, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(code, name, active, description);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static ConsentType register(String code, String name, Boolean active, String description) {
        var now = LocalDateTime.now();
        var aggregate = new ConsentType(ConsentTypeId.generate(), code, name, active, description, now, now);
        aggregate.recordEvent(new ConsentTypeRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ConsentType restore(ConsentTypeId id, String code, String name, Boolean active, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ConsentType(id, code, name, active, description, createdAt, updatedAt);
    }
    public void update(String code, String name, Boolean active, String description) {
        setDetails(code, name, active, description);
        updatedAt = LocalDateTime.now();
        recordEvent(new ConsentTypeUpdatedEvent(id, updatedAt));
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
    public ConsentTypeId id() { return id; }
    public String code() { return code; }
    public String name() { return name; }
    public String description() { return description; }
    public Boolean active() { return active; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}

