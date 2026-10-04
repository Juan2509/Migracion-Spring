package com.migracion.rangel.infrastructure.treatmentgoal.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.treatmentgoal.model.aggregate.TreatmentGoal;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.migracion.rangel.domain.treatmentgoal.port.repository.TreatmentGoalRepository;
import com.migracion.rangel.infrastructure.treatmentgoal.adapters.out.persistence.mappers.TreatmentGoalPersistenceMapper;
public class TreatmentGoalRepositoryAdapter implements TreatmentGoalRepository {
    private final TreatmentGoalJpaRepository repository;
    private final TreatmentGoalPersistenceMapper mapper;
    public TreatmentGoalRepositoryAdapter(TreatmentGoalJpaRepository repository, TreatmentGoalPersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    public TreatmentGoal save(TreatmentGoal aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<TreatmentGoal> findById(TreatmentGoalId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<TreatmentGoal> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(TreatmentGoal aggregate) { repository.deleteById(aggregate.id().value()); }
}

