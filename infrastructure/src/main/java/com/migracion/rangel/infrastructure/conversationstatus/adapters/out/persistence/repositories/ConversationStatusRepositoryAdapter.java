package com.migracion.rangel.infrastructure.conversationstatus.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.conversationstatus.model.aggregate.ConversationStatus;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;
import com.migracion.rangel.infrastructure.conversationstatus.adapters.out.persistence.mappers.ConversationStatusPersistenceMapper;
public class ConversationStatusRepositoryAdapter implements ConversationStatusRepository {
    private final ConversationStatusJpaRepository repository;
    private final ConversationStatusPersistenceMapper mapper;
    public ConversationStatusRepositoryAdapter(ConversationStatusJpaRepository repository, ConversationStatusPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ConversationStatus save(ConversationStatus aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ConversationStatus> findById(ConversationStatusId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ConversationStatus> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ConversationStatus aggregate) { repository.deleteById(aggregate.id().value()); }
}
