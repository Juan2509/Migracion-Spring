package com.migracion.rangel.domain.relationshiptype.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record RelationshipTypeId(UUID value) {
    public RelationshipTypeId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static RelationshipTypeId generate() { return new RelationshipTypeId(UUID.randomUUID()); }
}
