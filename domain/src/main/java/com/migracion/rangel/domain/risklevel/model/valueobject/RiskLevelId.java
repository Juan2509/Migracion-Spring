package com.migracion.rangel.domain.risklevel.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record RiskLevelId(UUID value) {
    public RiskLevelId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static RiskLevelId generate() { return new RiskLevelId(UUID.randomUUID()); }
}

