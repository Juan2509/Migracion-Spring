package com.migracion.rangel.infrastructure.encounter.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
public record CreateEncounterRequest(
        @NotNull UUID clinicalRecordId,
        @NotNull UUID professionalId,
        @NotNull UUID encounterTypeId,
        @NotNull OffsetDateTime startedAt,
        @NotNull OffsetDateTime endedAt,
        @NotNull String reasonForVisit,
        @NotNull String currentCondition,
        @NotNull UUID modalityId,
        @NotNull UUID statusId,
        @NotNull UUID createdBy,
        @NotNull UUID updatedBy) {}

