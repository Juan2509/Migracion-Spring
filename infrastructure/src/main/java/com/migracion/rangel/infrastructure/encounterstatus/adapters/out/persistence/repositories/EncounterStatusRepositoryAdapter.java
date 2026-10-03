package com.migracion.rangel.infrastructure.encounterstatus.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.encounterstatus.model.aggregate.EncounterStatus;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;
import com.migracion.rangel.infrastructure.encounterstatus.adapters.out.persistence.mappers.EncounterStatusPersistenceMapper;
public class EncounterStatusRepositoryAdapter implements EncounterStatusRepository {
    private final EncounterStatusJpaRepository repository;
    private final EncounterStatusPersistenceMapper mapper;
    public EncounterStatusRepositoryAdapter(EncounterStatusJpaRepository repository, EncounterStatusPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public EncounterStatus save(EncounterStatus aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<EncounterStatus> findById(EncounterStatusId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<EncounterStatus> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(EncounterStatus aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String value) { return repository.existsByCode(value); }
    public boolean existsByCodeAndIdNot(String value, EncounterStatusId id) {
        return repository.existsByCodeAndIdNot(value, id.value());
    }
    public boolean existsByName(String value) { return repository.existsByName(value); }
    public boolean existsByNameAndIdNot(String value, EncounterStatusId id) {
        return repository.existsByNameAndIdNot(value, id.value());
    }
}

