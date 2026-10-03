package com.migracion.rangel.infrastructure.patientcontact.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreatePatientContactRequest(
        @NotNull UUID contactId,
        @NotNull UUID patientId,
        @NotNull Boolean isPrimaryContact,
        @NotNull Boolean isEmergencyContact,
        @NotNull UUID relationshipTypeId) {}

