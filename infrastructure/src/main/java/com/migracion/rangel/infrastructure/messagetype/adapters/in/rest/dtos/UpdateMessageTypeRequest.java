package com.migracion.rangel.infrastructure.messagetype.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateMessageTypeRequest(
        @NotNull @Size(max = 50) String nameType
) {}
