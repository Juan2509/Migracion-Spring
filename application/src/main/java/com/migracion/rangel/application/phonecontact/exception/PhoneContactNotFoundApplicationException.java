package com.migracion.rangel.application.phonecontact.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.phonecontact.exception.PhoneContactNotFoundException;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
public class PhoneContactNotFoundApplicationException extends ApplicationException {
    public PhoneContactNotFoundApplicationException(PhoneContactId id) {
        super("PhoneContact no encontrado: " + id.value(), new PhoneContactNotFoundException(id));
    }
}
