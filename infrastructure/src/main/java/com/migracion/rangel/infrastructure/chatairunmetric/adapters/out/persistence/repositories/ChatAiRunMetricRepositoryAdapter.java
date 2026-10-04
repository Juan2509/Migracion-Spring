package com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.repositories;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import com.migracion.rangel.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.mappers.ChatAiRunMetricPersistenceMapper;
public class ChatAiRunMetricRepositoryAdapter implements ChatAiRunMetricRepository {
    private final ChatAiRunMetricJpaRepository repository;
    private final ChatAiRunMetricPersistenceMapper mapper;
    public ChatAiRunMetricRepositoryAdapter(ChatAiRunMetricJpaRepository repository, ChatAiRunMetricPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ChatAiRunMetric save(ChatAiRunMetric aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ChatAiRunMetric> findById(ChatAiRunMetricId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ChatAiRunMetric> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ChatAiRunMetric aggregate) { repository.deleteById(aggregate.id().value()); }
}

