package com.migracion.rangel.infrastructure.phonecontact.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdatePhoneContactRequest(
        @NotNull UUID contactId,
        @NotNull @Size(max = 30) String phone,
        String notes
) {}
