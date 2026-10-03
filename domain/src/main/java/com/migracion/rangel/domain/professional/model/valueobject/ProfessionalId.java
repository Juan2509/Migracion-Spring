package com.migracion.rangel.domain.professional.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ProfessionalId(UUID value) {
    public ProfessionalId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ProfessionalId generate() { return new ProfessionalId(UUID.randomUUID()); }
}
