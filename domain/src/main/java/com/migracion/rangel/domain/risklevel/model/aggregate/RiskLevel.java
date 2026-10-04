package com.migracion.rangel.domain.risklevel.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.domain.risklevel.event.RiskLevelRegisteredEvent;
import com.migracion.rangel.domain.risklevel.event.RiskLevelUpdatedEvent;

public final class RiskLevel extends AggregateRoot {
    private final RiskLevelId id;
    private String code;
    private String name;
    private Boolean active;
    private Integer severity;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private RiskLevel(RiskLevelId id, String code, String name, Boolean active, Integer severity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(code, name, active, severity);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static RiskLevel register(String code, String name, Boolean active, Integer severity) {
        var now = LocalDateTime.now();
        var aggregate = new RiskLevel(RiskLevelId.generate(), code, name, active, severity, now, now);
        aggregate.recordEvent(new RiskLevelRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static RiskLevel restore(RiskLevelId id, String code, String name, Boolean active, Integer severity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new RiskLevel(id, code, name, active, severity, createdAt, updatedAt);
    }
    public void update(String code, String name, Boolean active, Integer severity) {
        setDetails(code, name, active, severity);
        updatedAt = LocalDateTime.now();
        recordEvent(new RiskLevelUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String code, String name, Boolean active, Integer severity) {
        validateText(code, 20, "code");
        validateText(name, 50, "name");
        Objects.requireNonNull(active, "active es obligatorio");
        Objects.requireNonNull(severity, "severity es obligatorio");
        this.code = code;
        this.name = name;
        this.active = active;
        this.severity = severity;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public RiskLevelId id() { return id; }
    public String code() { return code; }
    public String name() { return name; }
    public Boolean active() { return active; }
    public Integer severity() { return severity; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}

