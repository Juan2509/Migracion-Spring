package com.migracion.rangel.infrastructure.mentalstatusexam.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
public record CreateMentalStatusExamRequest(
        @NotNull UUID encounterId,
        @NotNull String appearance,
        @NotNull String behavior,
        @NotNull String attitude,
        @NotNull String consciousness,
        @NotNull String orientation,
        @NotNull String attention,
        @NotNull String memory,
        @NotNull String speech,
        @NotNull String mood,
        @NotNull String affect,
        @NotNull String thoughtProcess,
        @NotNull String thoughtContent,
        @NotNull String perception,
        @NotNull String judgment,
        @NotNull String insight,
        @NotNull String psychomotorActivity,
        @NotNull String observations,
        @NotNull UUID createdBy) {}

