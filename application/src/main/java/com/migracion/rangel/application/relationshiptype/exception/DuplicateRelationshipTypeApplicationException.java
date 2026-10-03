package com.migracion.rangel.application.relationshiptype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateRelationshipTypeApplicationException extends ApplicationException {
    public DuplicateRelationshipTypeApplicationException() {
        super("Ya existe un RelationshipType con el mismo description.");
    }
}
