package com.migracion.rangel.infrastructure.encounter.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.encounter.model.aggregate.Encounter;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.infrastructure.encounter.adapters.out.persistence.mappers.EncounterPersistenceMapper;
public class EncounterRepositoryAdapter implements EncounterRepository {
    private final EncounterJpaRepository repository;
    private final EncounterPersistenceMapper mapper;
    public EncounterRepositoryAdapter(EncounterJpaRepository repository, EncounterPersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    public Encounter save(Encounter aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<Encounter> findById(EncounterId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<Encounter> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(Encounter aggregate) { repository.deleteById(aggregate.id().value()); }
}

