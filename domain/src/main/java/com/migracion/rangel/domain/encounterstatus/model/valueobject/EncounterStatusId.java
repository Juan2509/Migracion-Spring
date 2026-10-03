package com.migracion.rangel.domain.encounterstatus.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record EncounterStatusId(UUID value) {
    public EncounterStatusId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static EncounterStatusId generate() { return new EncounterStatusId(UUID.randomUUID()); }
}

