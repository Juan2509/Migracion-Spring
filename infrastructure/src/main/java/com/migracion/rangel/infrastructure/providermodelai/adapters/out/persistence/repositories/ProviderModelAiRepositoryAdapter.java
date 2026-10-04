package com.migracion.rangel.infrastructure.providermodelai.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.providermodelai.model.aggregate.ProviderModelAi;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
import com.migracion.rangel.domain.providermodelai.port.repository.ProviderModelAiRepository;
import com.migracion.rangel.infrastructure.providermodelai.adapters.out.persistence.mappers.ProviderModelAiPersistenceMapper;
public class ProviderModelAiRepositoryAdapter implements ProviderModelAiRepository {
    private final ProviderModelAiJpaRepository repository;
    private final ProviderModelAiPersistenceMapper mapper;
    public ProviderModelAiRepositoryAdapter(ProviderModelAiJpaRepository repository, ProviderModelAiPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ProviderModelAi save(ProviderModelAi aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ProviderModelAi> findById(ProviderModelAiId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ProviderModelAi> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ProviderModelAi aggregate) { repository.deleteById(aggregate.id().value()); }
}

