package com.migracion.rangel.infrastructure.airunstatus.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.airunstatus.model.aggregate.AiRunStatus;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
import com.migracion.rangel.infrastructure.airunstatus.adapters.out.persistence.mappers.AiRunStatusPersistenceMapper;
public class AiRunStatusRepositoryAdapter implements AiRunStatusRepository {
    private final AiRunStatusJpaRepository repository;
    private final AiRunStatusPersistenceMapper mapper;
    public AiRunStatusRepositoryAdapter(AiRunStatusJpaRepository repository, AiRunStatusPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public AiRunStatus save(AiRunStatus aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<AiRunStatus> findById(AiRunStatusId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<AiRunStatus> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(AiRunStatus aggregate) { repository.deleteById(aggregate.id().value()); }
}
