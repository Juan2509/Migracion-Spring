package com.migracion.rangel.infrastructure.treatmentplan.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.treatmentplan.model.aggregate.TreatmentPlan;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import com.migracion.rangel.infrastructure.treatmentplan.adapters.out.persistence.mappers.TreatmentPlanPersistenceMapper;
public class TreatmentPlanRepositoryAdapter implements TreatmentPlanRepository {
    private final TreatmentPlanJpaRepository repository;
    private final TreatmentPlanPersistenceMapper mapper;
    public TreatmentPlanRepositoryAdapter(TreatmentPlanJpaRepository repository, TreatmentPlanPersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    public TreatmentPlan save(TreatmentPlan aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<TreatmentPlan> findById(TreatmentPlanId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<TreatmentPlan> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(TreatmentPlan aggregate) { repository.deleteById(aggregate.id().value()); }
}


