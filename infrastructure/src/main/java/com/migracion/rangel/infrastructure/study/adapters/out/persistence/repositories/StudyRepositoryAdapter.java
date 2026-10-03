package com.migracion.rangel.infrastructure.study.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.study.model.aggregate.Study;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;
import com.migracion.rangel.infrastructure.study.adapters.out.persistence.mappers.StudyPersistenceMapper;
public class StudyRepositoryAdapter implements StudyRepository {
    private final StudyJpaRepository repository;
    private final StudyPersistenceMapper mapper;
    public StudyRepositoryAdapter(StudyJpaRepository repository, StudyPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public Study save(Study aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<Study> findById(StudyId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<Study> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(Study aggregate) { repository.deleteById(aggregate.id().value()); }
}
