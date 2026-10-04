package com.migracion.rangel.infrastructure.diagnosticsystem.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateDiagnosticSystemRequest(
        @NotNull @Size(max = 20) String code,
        @NotNull @Size(max = 50) String name,
        @NotNull Boolean active,
        @NotNull @Size(max = 20) String version
) {}

