package com.migracion.rangel.domain.emailcontact.exception;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
public class EmailContactNotFoundException extends RuntimeException {
    public EmailContactNotFoundException(EmailContactId id) { super("EmailContact no encontrado: " + id.value()); }
}
