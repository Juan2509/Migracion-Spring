package com.migracion.rangel.infrastructure.clinicalrecord.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateClinicalRecordRequest(
        @NotNull UUID patientId,
        @NotNull LocalDateTime creationDate,
        @NotNull @Size(max = 50) String recordNumber,
        @NotNull OffsetDateTime openedAt,
        @NotNull OffsetDateTime closedAt,
        @NotNull UUID statusId) {}

