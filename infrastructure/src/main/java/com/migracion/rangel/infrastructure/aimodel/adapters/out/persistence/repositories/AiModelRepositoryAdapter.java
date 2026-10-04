package com.migracion.rangel.infrastructure.aimodel.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.aimodel.model.aggregate.AiModel;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.infrastructure.aimodel.adapters.out.persistence.mappers.AiModelPersistenceMapper;
public class AiModelRepositoryAdapter implements AiModelRepository {
    private final AiModelJpaRepository repository;
    private final AiModelPersistenceMapper mapper;
    public AiModelRepositoryAdapter(AiModelJpaRepository repository, AiModelPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public AiModel save(AiModel aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<AiModel> findById(AiModelId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<AiModel> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(AiModel aggregate) { repository.deleteById(aggregate.id().value()); }
}

