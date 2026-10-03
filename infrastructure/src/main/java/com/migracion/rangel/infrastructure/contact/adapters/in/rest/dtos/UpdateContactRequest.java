package com.migracion.rangel.infrastructure.contact.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateContactRequest(
        @NotNull @Size(max = 200) String fullName,
        @NotNull @Size(max = 150) String email,
        @NotNull String notes,
        @NotNull UUID cityId,
        UUID updatedBy
) {}
