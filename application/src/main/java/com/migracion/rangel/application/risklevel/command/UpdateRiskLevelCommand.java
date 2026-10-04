package com.migracion.rangel.application.risklevel.command;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
public record UpdateRiskLevelCommand(RiskLevelId id, String code, String name, Boolean active, Integer severity) {}

