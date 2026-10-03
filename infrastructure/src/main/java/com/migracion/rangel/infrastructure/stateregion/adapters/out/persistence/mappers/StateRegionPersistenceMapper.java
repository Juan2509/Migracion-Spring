package com.migracion.rangel.infrastructure.stateregion.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.stateregion.model.aggregate.StateRegion;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.infrastructure.stateregion.adapters.out.persistence.entity.StateRegionJpaEntity;

public class StateRegionPersistenceMapper {
    public StateRegionJpaEntity toJpa(StateRegion region) {
        if (region == null) { return null; }
        var entity = new StateRegionJpaEntity();
        entity.setId(region.id().value());
        entity.setNameRegion(region.nameRegion());
        entity.setCodeRegion(region.codeRegion());
        entity.setDescription(region.description());
        entity.setIsActive(region.isActive());
        entity.setCountryId(region.countryId().value());
        entity.setCreatedAt(region.createdAt());
        entity.setUpdatedAt(region.updatedAt());
        return entity;
    }
    public StateRegion toDomain(StateRegionJpaEntity entity) {
        if (entity == null) { return null; }
        return StateRegion.restore(new StateRegionId(entity.getId()), entity.getNameRegion(),
                entity.getCodeRegion(), entity.getDescription(), entity.getIsActive(),
                new CountryId(entity.getCountryId()), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
