package com.migracion.rangel.infrastructure.country.adapters.out.persistence.mappers;

import com.migracion.rangel.domain.country.model.aggregate.Country;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.infrastructure.country.adapters.out.persistence.entity.CountryJpaEntity;

public class CountryPersistenceMapper {
    public CountryJpaEntity toJpa(Country country) {
        if (country == null) { return null; }
        var entity = new CountryJpaEntity();
        entity.setId(country.id().value());
        entity.setNameCountry(country.nameCountry());
        entity.setCodeCountry(country.codeCountry());
        entity.setDescription(country.description());
        entity.setIsActive(country.isActive());
        entity.setTelephonePrefix(country.telephonePrefix());
        entity.setCreatedAt(country.createdAt());
        entity.setUpdatedAt(country.updatedAt());
        return entity;
    }

    public Country toDomain(CountryJpaEntity entity) {
        if (entity == null) { return null; }
        return Country.restore(new CountryId(entity.getId()),
                entity.getNameCountry(), entity.getCodeCountry(), entity.getDescription(), entity.getIsActive(), entity.getTelephonePrefix(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
