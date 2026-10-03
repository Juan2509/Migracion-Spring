package com.migracion.rangel.infrastructure.clinicalnote.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateClinicalNoteRequest(
        @NotNull UUID encounterId,
        @NotNull String subjective,
        @NotNull String objective,
        @NotNull String assessment,
        @NotNull String plan,
        @NotNull String additionalNotes,
        @NotNull OffsetDateTime signedAt,
        @NotNull UUID professionalId) {}


