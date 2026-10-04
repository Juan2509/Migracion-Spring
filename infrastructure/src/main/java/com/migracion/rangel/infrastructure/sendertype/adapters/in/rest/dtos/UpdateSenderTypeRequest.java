package com.migracion.rangel.infrastructure.sendertype.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateSenderTypeRequest(
        @NotNull @Size(max = 50) String nameType
) {}
