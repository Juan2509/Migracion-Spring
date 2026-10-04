package com.migracion.rangel.infrastructure.sendertype.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.sendertype.model.aggregate.SenderType;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;
import com.migracion.rangel.infrastructure.sendertype.adapters.out.persistence.mappers.SenderTypePersistenceMapper;
public class SenderTypeRepositoryAdapter implements SenderTypeRepository {
    private final SenderTypeJpaRepository repository;
    private final SenderTypePersistenceMapper mapper;
    public SenderTypeRepositoryAdapter(SenderTypeJpaRepository repository, SenderTypePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public SenderType save(SenderType aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<SenderType> findById(SenderTypeId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<SenderType> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(SenderType aggregate) { repository.deleteById(aggregate.id().value()); }
}
