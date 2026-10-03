package com.migracion.rangel.infrastructure.professional.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateProfessionalRequest(
        @NotNull UUID documentTypeId,
        @NotNull @Size(max = 30) String documentNumber,
        @NotNull @Size(max = 60) String firstName,
        @NotNull @Size(max = 60) String lastName,
        @NotNull UUID professionalType,
        @NotNull @Size(max = 100) String licenseNumber,
        @NotNull Boolean active,
        @NotNull UUID cityId
) {}
