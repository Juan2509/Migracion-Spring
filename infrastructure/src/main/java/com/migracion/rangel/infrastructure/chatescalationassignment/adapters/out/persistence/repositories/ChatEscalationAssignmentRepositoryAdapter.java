package com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.migracion.rangel.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.mappers.ChatEscalationAssignmentPersistenceMapper;
public class ChatEscalationAssignmentRepositoryAdapter implements ChatEscalationAssignmentRepository {
    private final ChatEscalationAssignmentJpaRepository repository;
    private final ChatEscalationAssignmentPersistenceMapper mapper;
    public ChatEscalationAssignmentRepositoryAdapter(ChatEscalationAssignmentJpaRepository repository, ChatEscalationAssignmentPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ChatEscalationAssignment save(ChatEscalationAssignment aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ChatEscalationAssignment> findById(ChatEscalationAssignmentId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ChatEscalationAssignment> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ChatEscalationAssignment aggregate) { repository.deleteById(aggregate.id().value()); }
}

