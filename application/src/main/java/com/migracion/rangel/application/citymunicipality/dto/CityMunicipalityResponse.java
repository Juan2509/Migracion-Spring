package com.migracion.rangel.application.citymunicipality.dto;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
public record CityMunicipalityResponse(UUID id, String nameCity, String codeCiti,
        String description, Boolean isActive, UUID regionId,
        OffsetDateTime createdAt, LocalDateTime updatedAt) {
    public static CityMunicipalityResponse from(CityMunicipality city) {
        return new CityMunicipalityResponse(city.id().value(), city.nameCity(), city.codeCiti(),
                city.description(), city.isActive(), city.regionId().value(),
                city.createdAt(), city.updatedAt());
    }
}
