package com.migracion.rangel.infrastructure.country.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateCountryRequest(
        @NotNull @Size(max = 50) String nameCountry,
        @NotNull @Size(max = 10) String codeCountry,
        @NotNull @Size(max = 100) String description,
        @NotNull Boolean isActive,
        @NotNull @Size(max = 5) String telephonePrefix
) {}
