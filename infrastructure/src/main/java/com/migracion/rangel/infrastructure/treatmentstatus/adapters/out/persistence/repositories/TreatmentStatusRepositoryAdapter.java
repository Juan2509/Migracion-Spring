package com.migracion.rangel.infrastructure.treatmentstatus.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.treatmentstatus.model.aggregate.TreatmentStatus;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.migracion.rangel.infrastructure.treatmentstatus.adapters.out.persistence.mappers.TreatmentStatusPersistenceMapper;
public class TreatmentStatusRepositoryAdapter implements TreatmentStatusRepository {
    private final TreatmentStatusJpaRepository repository;
    private final TreatmentStatusPersistenceMapper mapper;
    public TreatmentStatusRepositoryAdapter(TreatmentStatusJpaRepository repository, TreatmentStatusPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public TreatmentStatus save(TreatmentStatus aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<TreatmentStatus> findById(TreatmentStatusId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<TreatmentStatus> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(TreatmentStatus aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String value) { return repository.existsByCode(value); }
    public boolean existsByCodeAndIdNot(String value, TreatmentStatusId id) {
        return repository.existsByCodeAndIdNot(value, id.value());
    }
    public boolean existsByName(String value) { return repository.existsByName(value); }
    public boolean existsByNameAndIdNot(String value, TreatmentStatusId id) {
        return repository.existsByNameAndIdNot(value, id.value());
    }
}


