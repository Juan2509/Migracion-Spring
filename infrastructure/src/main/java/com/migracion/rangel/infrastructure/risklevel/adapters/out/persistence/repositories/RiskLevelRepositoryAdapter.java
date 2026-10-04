package com.migracion.rangel.infrastructure.risklevel.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.risklevel.model.aggregate.RiskLevel;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
import com.migracion.rangel.infrastructure.risklevel.adapters.out.persistence.mappers.RiskLevelPersistenceMapper;
public class RiskLevelRepositoryAdapter implements RiskLevelRepository {
    private final RiskLevelJpaRepository repository;
    private final RiskLevelPersistenceMapper mapper;
    public RiskLevelRepositoryAdapter(RiskLevelJpaRepository repository, RiskLevelPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public RiskLevel save(RiskLevel aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<RiskLevel> findById(RiskLevelId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<RiskLevel> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(RiskLevel aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String value) { return repository.existsByCode(value); }
    public boolean existsByCodeAndIdNot(String value, RiskLevelId id) {
        return repository.existsByCodeAndIdNot(value, id.value());
    }
}

