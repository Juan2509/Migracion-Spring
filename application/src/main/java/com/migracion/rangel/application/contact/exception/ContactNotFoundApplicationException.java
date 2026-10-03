package com.migracion.rangel.application.contact.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.contact.exception.ContactNotFoundException;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
public class ContactNotFoundApplicationException extends ApplicationException {
    public ContactNotFoundApplicationException(ContactId id) {
        super("No existe el contacto " + id.value(), new ContactNotFoundException(id));
    }
}
