package com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.mappers.ChatConversationPersistenceMapper;
public class ChatConversationRepositoryAdapter implements ChatConversationRepository {
    private final ChatConversationJpaRepository repository;
    private final ChatConversationPersistenceMapper mapper;
    public ChatConversationRepositoryAdapter(ChatConversationJpaRepository repository, ChatConversationPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ChatConversation save(ChatConversation aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ChatConversation> findById(ChatConversationId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ChatConversation> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ChatConversation aggregate) { repository.deleteById(aggregate.id().value()); }
}

