package com.migracion.rangel.infrastructure.messagetype.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.messagetype.model.aggregate.MessageType;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.infrastructure.messagetype.adapters.out.persistence.mappers.MessageTypePersistenceMapper;
public class MessageTypeRepositoryAdapter implements MessageTypeRepository {
    private final MessageTypeJpaRepository repository;
    private final MessageTypePersistenceMapper mapper;
    public MessageTypeRepositoryAdapter(MessageTypeJpaRepository repository, MessageTypePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public MessageType save(MessageType aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<MessageType> findById(MessageTypeId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<MessageType> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(MessageType aggregate) { repository.deleteById(aggregate.id().value()); }
}
