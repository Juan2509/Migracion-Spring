package com.migracion.rangel.infrastructure.stateregion.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateStateRegionRequest(
        @NotNull @Size(max = 50) String nameRegion,
        @NotNull @Size(max = 10) String codeRegion,
        @NotNull @Size(max = 100) String description,
        @NotNull Boolean isActive,
        @NotNull UUID countryId
) {}
