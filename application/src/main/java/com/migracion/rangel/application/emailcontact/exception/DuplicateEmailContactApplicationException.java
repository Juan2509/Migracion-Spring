package com.migracion.rangel.application.emailcontact.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateEmailContactApplicationException extends ApplicationException {
    public DuplicateEmailContactApplicationException() { super("Ya existe un email_contact con ese correo."); }
}
