package com.migracion.rangel.domain.diagnosticsystem.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record DiagnosticSystemId(UUID value) {
    public DiagnosticSystemId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static DiagnosticSystemId generate() { return new DiagnosticSystemId(UUID.randomUUID()); }
}

