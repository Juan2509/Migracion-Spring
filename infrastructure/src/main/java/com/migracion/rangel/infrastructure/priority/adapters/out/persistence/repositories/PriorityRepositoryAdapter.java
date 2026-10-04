package com.migracion.rangel.infrastructure.priority.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.priority.model.aggregate.Priority;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;
import com.migracion.rangel.infrastructure.priority.adapters.out.persistence.mappers.PriorityPersistenceMapper;
public class PriorityRepositoryAdapter implements PriorityRepository {
    private final PriorityJpaRepository repository;
    private final PriorityPersistenceMapper mapper;
    public PriorityRepositoryAdapter(PriorityJpaRepository repository, PriorityPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public Priority save(Priority aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<Priority> findById(PriorityId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<Priority> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(Priority aggregate) { repository.deleteById(aggregate.id().value()); }
}
