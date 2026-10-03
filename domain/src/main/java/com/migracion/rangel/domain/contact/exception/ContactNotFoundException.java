package com.migracion.rangel.domain.contact.exception;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(ContactId id) { super("No existe el contacto " + id.value()); }
}
