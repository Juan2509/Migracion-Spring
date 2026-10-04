package com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.migracion.rangel.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.out.persistence.mappers.ChatEscalationStatusHistoryPersistenceMapper;
public class ChatEscalationStatusHistoryRepositoryAdapter implements ChatEscalationStatusHistoryRepository {
    private final ChatEscalationStatusHistoryJpaRepository repository;
    private final ChatEscalationStatusHistoryPersistenceMapper mapper;
    public ChatEscalationStatusHistoryRepositoryAdapter(ChatEscalationStatusHistoryJpaRepository repository, ChatEscalationStatusHistoryPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ChatEscalationStatusHistory save(ChatEscalationStatusHistory aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ChatEscalationStatusHistory> findById(ChatEscalationStatusHistoryId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ChatEscalationStatusHistory> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ChatEscalationStatusHistory aggregate) { repository.deleteById(aggregate.id().value()); }
}

