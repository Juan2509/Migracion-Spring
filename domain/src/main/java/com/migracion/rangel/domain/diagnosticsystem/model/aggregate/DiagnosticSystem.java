package com.migracion.rangel.domain.diagnosticsystem.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.migracion.rangel.domain.diagnosticsystem.event.DiagnosticSystemRegisteredEvent;
import com.migracion.rangel.domain.diagnosticsystem.event.DiagnosticSystemUpdatedEvent;

public final class DiagnosticSystem extends AggregateRoot {
    private final DiagnosticSystemId id;
    private String code;
    private String name;
    private Boolean active;
    private String version;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private DiagnosticSystem(DiagnosticSystemId id, String code, String name, Boolean active, String version, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(code, name, active, version);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static DiagnosticSystem register(String code, String name, Boolean active, String version) {
        var now = LocalDateTime.now();
        var aggregate = new DiagnosticSystem(DiagnosticSystemId.generate(), code, name, active, version, now, now);
        aggregate.recordEvent(new DiagnosticSystemRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static DiagnosticSystem restore(DiagnosticSystemId id, String code, String name, Boolean active, String version, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new DiagnosticSystem(id, code, name, active, version, createdAt, updatedAt);
    }
    public void update(String code, String name, Boolean active, String version) {
        setDetails(code, name, active, version);
        updatedAt = LocalDateTime.now();
        recordEvent(new DiagnosticSystemUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String code, String name, Boolean active, String version) {
        validateText(code, 20, "code");
        validateText(name, 50, "name");
        Objects.requireNonNull(active, "active es obligatorio");
        validateText(version, 20, "version");
        this.version = version;
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
    public DiagnosticSystemId id() { return id; }
    public String code() { return code; }
    public String name() { return name; }
    public String version() { return version; }
    public Boolean active() { return active; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}

