package com.migracion.rangel.infrastructure.assessmenttype.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.assessmenttype.model.aggregate.AssessmentType;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import com.migracion.rangel.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import com.migracion.rangel.infrastructure.assessmenttype.adapters.out.persistence.mappers.AssessmentTypePersistenceMapper;
public class AssessmentTypeRepositoryAdapter implements AssessmentTypeRepository {
    private final AssessmentTypeJpaRepository repository;
    private final AssessmentTypePersistenceMapper mapper;
    public AssessmentTypeRepositoryAdapter(AssessmentTypeJpaRepository repository, AssessmentTypePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public AssessmentType save(AssessmentType aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<AssessmentType> findById(AssessmentTypeId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<AssessmentType> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(AssessmentType aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String value) { return repository.existsByCode(value); }
    public boolean existsByCodeAndIdNot(String value, AssessmentTypeId id) {
        return repository.existsByCodeAndIdNot(value, id.value());
    }
}

