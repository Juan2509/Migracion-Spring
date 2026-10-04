package com.migracion.rangel.application.risklevel.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateRiskLevelApplicationException extends ApplicationException {
    public DuplicateRiskLevelApplicationException() {
        super("Ya existe un RiskLevel con el mismo code.");
    }
}

