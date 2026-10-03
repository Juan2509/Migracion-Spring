package com.migracion.rangel.application.emailcontact.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.emailcontact.exception.EmailContactNotFoundException;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
public class EmailContactNotFoundApplicationException extends ApplicationException {
    public EmailContactNotFoundApplicationException(EmailContactId id) {
        super("EmailContact no encontrado: " + id.value(), new EmailContactNotFoundException(id));
    }
}
