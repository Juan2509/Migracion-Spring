package com.migracion.rangel.domain.documenttype.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record DocumentTypeId(UUID value) {
    public DocumentTypeId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static DocumentTypeId generate() { return new DocumentTypeId(UUID.randomUUID()); }
}
