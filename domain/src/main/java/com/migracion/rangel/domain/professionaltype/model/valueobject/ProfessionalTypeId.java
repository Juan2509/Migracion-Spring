package com.migracion.rangel.domain.professionaltype.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ProfessionalTypeId(UUID value) {
    public ProfessionalTypeId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ProfessionalTypeId generate() { return new ProfessionalTypeId(UUID.randomUUID()); }
}
