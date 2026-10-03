package com.migracion.rangel.application.stateregion.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.stateregion.exception.StateRegionNotFoundException;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
public class StateRegionNotFoundApplicationException extends ApplicationException {
    public StateRegionNotFoundApplicationException(StateRegionId id) {
        super("No existe la región " + id.value(), new StateRegionNotFoundException(id));
    }
}
