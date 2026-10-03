package com.migracion.rangel.domain.encounterstatus.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.domain.encounterstatus.event.EncounterStatusRegisteredEvent;
import com.migracion.rangel.domain.encounterstatus.event.EncounterStatusUpdatedEvent;

public final class EncounterStatus extends AggregateRoot {
    private final EncounterStatusId id;
    private String code;
    private String name;
    private Boolean active;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private EncounterStatus(EncounterStatusId id, String code, String name, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(code, name, active);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static EncounterStatus register(String code, String name, Boolean active) {
        var now = LocalDateTime.now();
        var aggregate = new EncounterStatus(EncounterStatusId.generate(), code, name, active, now, now);
        aggregate.recordEvent(new EncounterStatusRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static EncounterStatus restore(EncounterStatusId id, String code, String name, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new EncounterStatus(id, code, name, active, createdAt, updatedAt);
    }
    public void update(String code, String name, Boolean active) {
        setDetails(code, name, active);
        updatedAt = LocalDateTime.now();
        recordEvent(new EncounterStatusUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String code, String name, Boolean active) {
        validateText(code, 20, "code");
        validateText(name, 50, "name");
        Objects.requireNonNull(active, "active es obligatorio");
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
    public EncounterStatusId id() { return id; }
    public String code() { return code; }
    public String name() { return name; }
    public Boolean active() { return active; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}

