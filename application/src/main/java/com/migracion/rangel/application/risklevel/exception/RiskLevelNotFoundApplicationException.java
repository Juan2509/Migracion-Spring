package com.migracion.rangel.application.risklevel.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.risklevel.exception.RiskLevelNotFoundException;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
public class RiskLevelNotFoundApplicationException extends ApplicationException {
    public RiskLevelNotFoundApplicationException(RiskLevelId id) {
        super("RiskLevel no encontrado: " + id.value(), new RiskLevelNotFoundException(id));
    }
}

