package com.migracion.rangel.domain.risklevel.exception;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
public class RiskLevelNotFoundException extends RuntimeException {
    public RiskLevelNotFoundException(RiskLevelId id) { super("RiskLevel no encontrado: " + id.value()); }
}

