package com.migracion.rangel.infrastructure.patientallergy.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdatePatientAllergyRequest(
        @NotNull UUID patientId,
        @NotNull @Size(max = 200) String substance,
        String reaction,
        @NotNull @Size(max = 20) String severity,
        @NotNull Boolean active,
        @NotNull OffsetDateTime recordedAt,
        @NotNull UUID recordedBy) {}

