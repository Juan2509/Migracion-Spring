package com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.repositories;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatescalation.model.aggregate.ChatEscalation;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.mappers.ChatEscalationPersistenceMapper;
public class ChatEscalationRepositoryAdapter implements ChatEscalationRepository {
    private final ChatEscalationJpaRepository repository;
    private final ChatEscalationPersistenceMapper mapper;
    public ChatEscalationRepositoryAdapter(ChatEscalationJpaRepository repository, ChatEscalationPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ChatEscalation save(ChatEscalation aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ChatEscalation> findById(ChatEscalationId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ChatEscalation> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ChatEscalation aggregate) { repository.deleteById(aggregate.id().value()); }
}

