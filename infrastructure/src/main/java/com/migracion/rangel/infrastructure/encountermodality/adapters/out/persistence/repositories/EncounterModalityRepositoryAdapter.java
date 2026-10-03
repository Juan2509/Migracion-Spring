package com.migracion.rangel.infrastructure.encountermodality.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.encountermodality.model.aggregate.EncounterModality;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;
import com.migracion.rangel.infrastructure.encountermodality.adapters.out.persistence.mappers.EncounterModalityPersistenceMapper;
public class EncounterModalityRepositoryAdapter implements EncounterModalityRepository {
    private final EncounterModalityJpaRepository repository;
    private final EncounterModalityPersistenceMapper mapper;
    public EncounterModalityRepositoryAdapter(EncounterModalityJpaRepository repository, EncounterModalityPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public EncounterModality save(EncounterModality aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<EncounterModality> findById(EncounterModalityId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<EncounterModality> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(EncounterModality aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String value) { return repository.existsByCode(value); }
    public boolean existsByCodeAndIdNot(String value, EncounterModalityId id) {
        return repository.existsByCodeAndIdNot(value, id.value());
    }
    public boolean existsByName(String value) { return repository.existsByName(value); }
    public boolean existsByNameAndIdNot(String value, EncounterModalityId id) {
        return repository.existsByNameAndIdNot(value, id.value());
    }
}

