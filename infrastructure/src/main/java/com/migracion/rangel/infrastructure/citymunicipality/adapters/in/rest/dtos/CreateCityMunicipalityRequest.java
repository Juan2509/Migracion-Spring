package com.migracion.rangel.infrastructure.citymunicipality.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateCityMunicipalityRequest(
        @NotNull @Size(max = 50) String nameCity,
        @NotNull @Size(max = 10) String codeCiti,
        @NotNull @Size(max = 100) String description,
        @NotNull Boolean isActive,
        @NotNull UUID regionId
) {}
