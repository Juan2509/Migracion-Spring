package com.migracion.rangel.infrastructure.chatairunerror.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatairunerror.model.aggregate.ChatAiRunError;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.migracion.rangel.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import com.migracion.rangel.infrastructure.chatairunerror.adapters.out.persistence.mappers.ChatAiRunErrorPersistenceMapper;
public class ChatAiRunErrorRepositoryAdapter implements ChatAiRunErrorRepository {
    private final ChatAiRunErrorJpaRepository repository;
    private final ChatAiRunErrorPersistenceMapper mapper;
    public ChatAiRunErrorRepositoryAdapter(ChatAiRunErrorJpaRepository repository, ChatAiRunErrorPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ChatAiRunError save(ChatAiRunError aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ChatAiRunError> findById(ChatAiRunErrorId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ChatAiRunError> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ChatAiRunError aggregate) { repository.deleteById(aggregate.id().value()); }
}

