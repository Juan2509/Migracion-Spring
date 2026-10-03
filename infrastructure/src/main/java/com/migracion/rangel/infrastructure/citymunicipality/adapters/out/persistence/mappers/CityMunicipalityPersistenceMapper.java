package com.migracion.rangel.infrastructure.citymunicipality.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.infrastructure.citymunicipality.adapters.out.persistence.entity.CityMunicipalityJpaEntity;

public class CityMunicipalityPersistenceMapper {
    public CityMunicipalityJpaEntity toJpa(CityMunicipality city) {
        if (city == null) { return null; }
        var entity = new CityMunicipalityJpaEntity();
        entity.setId(city.id().value());
        entity.setNameCity(city.nameCity());
        entity.setCodeCiti(city.codeCiti());
        entity.setDescription(city.description());
        entity.setIsActive(city.isActive());
        entity.setRegionId(city.regionId().value());
        entity.setCreatedAt(city.createdAt());
        entity.setUpdatedAt(city.updatedAt());
        return entity;
    }
    public CityMunicipality toDomain(CityMunicipalityJpaEntity entity) {
        if (entity == null) { return null; }
        return CityMunicipality.restore(new CityMunicipalityId(entity.getId()), entity.getNameCity(),
                entity.getCodeCiti(), entity.getDescription(), entity.getIsActive(),
                new StateRegionId(entity.getRegionId()), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
