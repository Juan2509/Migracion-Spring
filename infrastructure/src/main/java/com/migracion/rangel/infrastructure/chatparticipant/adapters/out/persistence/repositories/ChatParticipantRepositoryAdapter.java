package com.migracion.rangel.infrastructure.chatparticipant.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatparticipant.model.aggregate.ChatParticipant;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.infrastructure.chatparticipant.adapters.out.persistence.mappers.ChatParticipantPersistenceMapper;
public class ChatParticipantRepositoryAdapter implements ChatParticipantRepository {
    private final ChatParticipantJpaRepository repository;
    private final ChatParticipantPersistenceMapper mapper;
    public ChatParticipantRepositoryAdapter(ChatParticipantJpaRepository repository, ChatParticipantPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ChatParticipant save(ChatParticipant aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ChatParticipant> findById(ChatParticipantId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ChatParticipant> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ChatParticipant aggregate) { repository.deleteById(aggregate.id().value()); }
}

