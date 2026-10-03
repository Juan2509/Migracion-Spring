package com.migracion.rangel.application.country.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.country.model.aggregate.Country;
public record CountryResponse(UUID id, String nameCountry, String codeCountry, String description, Boolean isActive, String telephonePrefix, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static CountryResponse from(Country country) {
        return new CountryResponse(country.id().value(), country.nameCountry(), country.codeCountry(), country.description(), country.isActive(), country.telephonePrefix(), country.createdAt(), country.updatedAt());
    }
}
