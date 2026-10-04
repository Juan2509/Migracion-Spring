package com.migracion.rangel.domain.escalationstatus.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record EscalationStatusId(UUID value) {
    public EscalationStatusId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static EscalationStatusId generate() { return new EscalationStatusId(UUID.randomUUID()); }
}
