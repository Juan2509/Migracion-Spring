package com.migracion.rangel.domain.encountermodality.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record EncounterModalityId(UUID value) {
    public EncounterModalityId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static EncounterModalityId generate() { return new EncounterModalityId(UUID.randomUUID()); }
}

