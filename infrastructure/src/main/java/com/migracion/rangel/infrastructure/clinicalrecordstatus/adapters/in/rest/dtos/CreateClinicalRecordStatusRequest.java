package com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateClinicalRecordStatusRequest(
        @NotNull @Size(max = 20) String code,
        @NotNull @Size(max = 50) String name) {}

