package com.migracion.rangel.domain.encountertype.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record EncounterTypeId(UUID value) {
    public EncounterTypeId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static EncounterTypeId generate() { return new EncounterTypeId(UUID.randomUUID()); }
}

