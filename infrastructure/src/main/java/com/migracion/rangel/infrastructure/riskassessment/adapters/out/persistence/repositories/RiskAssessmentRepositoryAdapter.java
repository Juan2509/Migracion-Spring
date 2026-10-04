package com.migracion.rangel.infrastructure.riskassessment.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.riskassessment.model.aggregate.RiskAssessment;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
import com.migracion.rangel.domain.riskassessment.port.repository.RiskAssessmentRepository;
import com.migracion.rangel.infrastructure.riskassessment.adapters.out.persistence.mappers.RiskAssessmentPersistenceMapper;
public class RiskAssessmentRepositoryAdapter implements RiskAssessmentRepository {
    private final RiskAssessmentJpaRepository repository;
    private final RiskAssessmentPersistenceMapper mapper;
    public RiskAssessmentRepositoryAdapter(RiskAssessmentJpaRepository repository, RiskAssessmentPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public RiskAssessment save(RiskAssessment aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<RiskAssessment> findById(RiskAssessmentId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<RiskAssessment> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(RiskAssessment aggregate) { repository.deleteById(aggregate.id().value()); }

}

