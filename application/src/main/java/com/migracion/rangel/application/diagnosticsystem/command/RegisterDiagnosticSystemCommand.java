package com.migracion.rangel.application.diagnosticsystem.command;

public record RegisterDiagnosticSystemCommand(String code, String name, Boolean active, String version) {}

