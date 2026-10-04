package com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.escalationstatus.model.aggregate.EscalationStatus;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.mappers.EscalationStatusPersistenceMapper;
public class EscalationStatusRepositoryAdapter implements EscalationStatusRepository {
    private final EscalationStatusJpaRepository repository;
    private final EscalationStatusPersistenceMapper mapper;
    public EscalationStatusRepositoryAdapter(EscalationStatusJpaRepository repository, EscalationStatusPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public EscalationStatus save(EscalationStatus aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<EscalationStatus> findById(EscalationStatusId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<EscalationStatus> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(EscalationStatus aggregate) { repository.deleteById(aggregate.id().value()); }
}
