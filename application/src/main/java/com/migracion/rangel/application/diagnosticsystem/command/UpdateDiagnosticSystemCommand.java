package com.migracion.rangel.application.diagnosticsystem.command;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
public record UpdateDiagnosticSystemCommand(DiagnosticSystemId id, String code, String name, Boolean active, String version) {}

