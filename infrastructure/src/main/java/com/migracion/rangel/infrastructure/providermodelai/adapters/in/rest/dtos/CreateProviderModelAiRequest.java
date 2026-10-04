package com.migracion.rangel.infrastructure.providermodelai.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateProviderModelAiRequest(
        @NotNull @Size(max = 100) String nameProviderAi,
        @NotNull String razonSocial,
        @NotNull Boolean isActive,
        @NotNull String sitioWeb
) {}

