package com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatmessage.model.aggregate.ChatMessage;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.mappers.ChatMessagePersistenceMapper;
public class ChatMessageRepositoryAdapter implements ChatMessageRepository {
    private final ChatMessageJpaRepository repository;
    private final ChatMessagePersistenceMapper mapper;
    public ChatMessageRepositoryAdapter(ChatMessageJpaRepository repository, ChatMessagePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ChatMessage save(ChatMessage aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ChatMessage> findById(ChatMessageId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ChatMessage> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ChatMessage aggregate) { repository.deleteById(aggregate.id().value()); }
}

