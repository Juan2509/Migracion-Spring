package com.migracion.rangel.domain.stateregion.exception;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
public class StateRegionNotFoundException extends RuntimeException {
    public StateRegionNotFoundException(StateRegionId id) { super("No existe la región " + id.value()); }
}
