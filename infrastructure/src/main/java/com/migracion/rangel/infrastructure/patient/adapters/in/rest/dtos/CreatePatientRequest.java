package com.migracion.rangel.infrastructure.patient.adapters.in.rest.dtos;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreatePatientRequest(
        @NotNull UUID documentTypeId,
        @NotNull @Size(max = 30) String documentNumber,
        @NotNull @Size(max = 50) String firstName,
        @Size(max = 50) String middleName,
        @NotNull @Size(max = 50) String lastName,
        @Size(max = 50) String secondLastName,
        @NotNull LocalDate birthDate,
        @NotNull UUID biologicalSexId,
        @NotNull UUID genderIdentity,
        @NotNull @Size(max = 150) String email,
        @NotNull @Size(max = 30) String phone,
        @NotNull @Size(max = 250) String address,
        @NotNull Boolean active,
        @NotNull UUID cityId,
        UUID createdBy) {}

