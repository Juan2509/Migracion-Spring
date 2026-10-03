package com.migracion.rangel.application.documenttype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.documenttype.exception.DocumentTypeNotFoundException;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
public class DocumentTypeNotFoundApplicationException extends ApplicationException {
    public DocumentTypeNotFoundApplicationException(DocumentTypeId id) {
        super("DocumentType no encontrado: " + id.value(), new DocumentTypeNotFoundException(id));
    }
}
