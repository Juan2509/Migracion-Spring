package com.migracion.rangel.domain.professionalstudy.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ProfessionalStudyId(UUID value) {
    public ProfessionalStudyId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ProfessionalStudyId generate() { return new ProfessionalStudyId(UUID.randomUUID()); }
}
