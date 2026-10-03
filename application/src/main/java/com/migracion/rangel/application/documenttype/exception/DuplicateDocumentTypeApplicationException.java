package com.migracion.rangel.application.documenttype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateDocumentTypeApplicationException extends ApplicationException {
    public DuplicateDocumentTypeApplicationException() {
        super("Ya existe un DocumentType con el mismo code.");
    }
}
