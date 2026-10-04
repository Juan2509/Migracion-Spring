package com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.out.persistence.mappers.TreatmentGoalStatusPersistenceMapper;
public class TreatmentGoalStatusRepositoryAdapter implements TreatmentGoalStatusRepository {
    private final TreatmentGoalStatusJpaRepository repository;
    private final TreatmentGoalStatusPersistenceMapper mapper;
    public TreatmentGoalStatusRepositoryAdapter(TreatmentGoalStatusJpaRepository repository, TreatmentGoalStatusPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public TreatmentGoalStatus save(TreatmentGoalStatus aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<TreatmentGoalStatus> findById(TreatmentGoalStatusId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<TreatmentGoalStatus> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(TreatmentGoalStatus aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String value) { return repository.existsByCode(value); }
    public boolean existsByCodeAndIdNot(String value, TreatmentGoalStatusId id) {
        return repository.existsByCodeAndIdNot(value, id.value());
    }
    public boolean existsByName(String value) { return repository.existsByName(value); }
    public boolean existsByNameAndIdNot(String value, TreatmentGoalStatusId id) {
        return repository.existsByNameAndIdNot(value, id.value());
    }
}



