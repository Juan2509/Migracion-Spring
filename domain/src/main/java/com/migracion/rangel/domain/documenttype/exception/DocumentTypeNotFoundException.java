package com.migracion.rangel.domain.documenttype.exception;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
public class DocumentTypeNotFoundException extends RuntimeException {
    public DocumentTypeNotFoundException(DocumentTypeId id) { super("DocumentType no encontrado: " + id.value()); }
}
