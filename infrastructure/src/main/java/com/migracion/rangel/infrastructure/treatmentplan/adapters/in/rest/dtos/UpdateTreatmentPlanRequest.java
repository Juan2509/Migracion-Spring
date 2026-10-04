package com.migracion.rangel.infrastructure.treatmentplan.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateTreatmentPlanRequest(
        @NotNull UUID encounterId,
        @NotNull @Size(max = 200) String title,
        @NotNull String description,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull UUID treatmentStatusId,
        @NotNull UUID professionalId) {}



