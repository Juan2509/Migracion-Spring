package com.migracion.rangel.domain.phonecontact.exception;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
public class PhoneContactNotFoundException extends RuntimeException {
    public PhoneContactNotFoundException(PhoneContactId id) { super("PhoneContact no encontrado: " + id.value()); }
}
