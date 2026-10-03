package com.migracion.rangel.application.relationshiptype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.relationshiptype.exception.RelationshipTypeNotFoundException;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
public class RelationshipTypeNotFoundApplicationException extends ApplicationException {
    public RelationshipTypeNotFoundApplicationException(RelationshipTypeId id) {
        super("RelationshipType no encontrado: " + id.value(), new RelationshipTypeNotFoundException(id));
    }
}
