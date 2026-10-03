package com.migracion.rangel.infrastructure.emailcontact.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateEmailContactRequest(
        @NotNull UUID contactId,
        @NotNull @Size(max = 150) String email,
        @NotNull String notes
) {}
