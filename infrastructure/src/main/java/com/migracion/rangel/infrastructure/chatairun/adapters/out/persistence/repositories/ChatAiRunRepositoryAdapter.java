package com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatairun.model.aggregate.ChatAiRun;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.mappers.ChatAiRunPersistenceMapper;
public class ChatAiRunRepositoryAdapter implements ChatAiRunRepository {
    private final ChatAiRunJpaRepository repository;
    private final ChatAiRunPersistenceMapper mapper;
    public ChatAiRunRepositoryAdapter(ChatAiRunJpaRepository repository, ChatAiRunPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ChatAiRun save(ChatAiRun aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ChatAiRun> findById(ChatAiRunId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ChatAiRun> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ChatAiRun aggregate) { repository.deleteById(aggregate.id().value()); }
}

