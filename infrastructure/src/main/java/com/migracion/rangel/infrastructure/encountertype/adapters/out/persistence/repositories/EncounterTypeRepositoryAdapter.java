package com.migracion.rangel.infrastructure.encountertype.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.encountertype.model.aggregate.EncounterType;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.domain.encountertype.port.repository.EncounterTypeRepository;
import com.migracion.rangel.infrastructure.encountertype.adapters.out.persistence.mappers.EncounterTypePersistenceMapper;
public class EncounterTypeRepositoryAdapter implements EncounterTypeRepository {
    private final EncounterTypeJpaRepository repository;
    private final EncounterTypePersistenceMapper mapper;
    public EncounterTypeRepositoryAdapter(EncounterTypeJpaRepository repository, EncounterTypePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public EncounterType save(EncounterType aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<EncounterType> findById(EncounterTypeId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<EncounterType> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(EncounterType aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String value) { return repository.existsByCode(value); }
    public boolean existsByCodeAndIdNot(String value, EncounterTypeId id) {
        return repository.existsByCodeAndIdNot(value, id.value());
    }
    public boolean existsByName(String value) { return repository.existsByName(value); }
    public boolean existsByNameAndIdNot(String value, EncounterTypeId id) {
        return repository.existsByNameAndIdNot(value, id.value());
    }
}

