package com.migracion.rangel.domain.gender.exception;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
public class GenderNotFoundException extends RuntimeException {
    public GenderNotFoundException(GenderId id) { super("Gender no encontrado: " + id.value()); }
}
