package com.migracion.rangel.domain.relationshiptype.exception;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
public class RelationshipTypeNotFoundException extends RuntimeException {
    public RelationshipTypeNotFoundException(RelationshipTypeId id) { super("RelationshipType no encontrado: " + id.value()); }
}
