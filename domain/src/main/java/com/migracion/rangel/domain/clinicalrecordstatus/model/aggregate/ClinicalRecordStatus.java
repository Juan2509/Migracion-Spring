package com.migracion.rangel.domain.clinicalrecordstatus.model.aggregate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.clinicalrecordstatus.event.ClinicalRecordStatusRegisteredEvent;
import com.migracion.rangel.domain.clinicalrecordstatus.event.ClinicalRecordStatusUpdatedEvent;
public final class ClinicalRecordStatus extends AggregateRoot {
    private final ClinicalRecordStatusId id;
    private String code;
    private String name;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ClinicalRecordStatus(ClinicalRecordStatusId id, String code, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(code, name);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");

    }
    public static ClinicalRecordStatus register(String code, String name) {
        var now = LocalDateTime.now();
        var aggregate = new ClinicalRecordStatus(ClinicalRecordStatusId.generate(), code, name, now, now);
        aggregate.recordEvent(new ClinicalRecordStatusRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ClinicalRecordStatus restore(ClinicalRecordStatusId id, String code, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ClinicalRecordStatus(id, code, name, createdAt, updatedAt);
    }
    public void update(String code, String name) {
        setDetails(code, name);
        var now = LocalDateTime.now();
        updatedAt = now;
        recordEvent(new ClinicalRecordStatusUpdatedEvent(id, now));
    }
    private void setDetails(String code, String name) {
        // Validar todo antes de modificar el estado.
        validateText(code, 20, "code");
        validateText(name, 50, "name");
        this.code = code;
        this.name = name;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public ClinicalRecordStatusId id() { return id; }
    public String code() { return code; }
    public String name() { return name; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
