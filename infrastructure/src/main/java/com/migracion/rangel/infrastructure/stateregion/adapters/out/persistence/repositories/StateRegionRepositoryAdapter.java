package com.migracion.rangel.infrastructure.stateregion.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.stateregion.model.aggregate.StateRegion;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.infrastructure.stateregion.adapters.out.persistence.mappers.StateRegionPersistenceMapper;

public class StateRegionRepositoryAdapter implements StateRegionRepository {
    private final StateRegionJpaRepository repository;
    private final StateRegionPersistenceMapper mapper;
    public StateRegionRepositoryAdapter(StateRegionJpaRepository repository, StateRegionPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public StateRegion save(StateRegion region) { return mapper.toDomain(repository.save(mapper.toJpa(region))); }
    public Optional<StateRegion> findById(StateRegionId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<StateRegion> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(StateRegion region) { repository.deleteById(region.id().value()); }
}
