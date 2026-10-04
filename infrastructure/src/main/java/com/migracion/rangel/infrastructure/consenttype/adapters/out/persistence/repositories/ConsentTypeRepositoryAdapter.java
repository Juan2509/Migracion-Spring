package com.migracion.rangel.infrastructure.consenttype.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.consenttype.model.aggregate.ConsentType;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
import com.migracion.rangel.domain.consenttype.port.repository.ConsentTypeRepository;
import com.migracion.rangel.infrastructure.consenttype.adapters.out.persistence.mappers.ConsentTypePersistenceMapper;
public class ConsentTypeRepositoryAdapter implements ConsentTypeRepository {
    private final ConsentTypeJpaRepository repository;
    private final ConsentTypePersistenceMapper mapper;
    public ConsentTypeRepositoryAdapter(ConsentTypeJpaRepository repository, ConsentTypePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ConsentType save(ConsentType aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ConsentType> findById(ConsentTypeId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ConsentType> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ConsentType aggregate) { repository.deleteById(aggregate.id().value()); }
}

