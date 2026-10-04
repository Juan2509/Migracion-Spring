package com.migracion.rangel.infrastructure.providermodelai.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.providermodelai.model.aggregate.ProviderModelAi;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
import com.migracion.rangel.infrastructure.providermodelai.adapters.out.persistence.entity.ProviderModelAiJpaEntity;
public class ProviderModelAiPersistenceMapper {
    public ProviderModelAiJpaEntity toJpa(ProviderModelAi aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ProviderModelAiJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setNameProviderAi(aggregate.nameProviderAi());
        entity.setRazonSocial(aggregate.razonSocial());
        entity.setIsActive(aggregate.isActive());
        entity.setSitioWeb(aggregate.sitioWeb());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ProviderModelAi toDomain(ProviderModelAiJpaEntity entity) {
        if (entity == null) { return null; }
        return ProviderModelAi.restore(new ProviderModelAiId(entity.getId()), entity.getNameProviderAi(), entity.getRazonSocial(), entity.getIsActive(), entity.getSitioWeb(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

