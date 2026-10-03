package com.migracion.rangel.infrastructure.mentalstatusexam.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.migracion.rangel.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;
import com.migracion.rangel.infrastructure.mentalstatusexam.adapters.out.persistence.mappers.MentalStatusExamPersistenceMapper;
public class MentalStatusExamRepositoryAdapter implements MentalStatusExamRepository {
    private final MentalStatusExamJpaRepository repository;
    private final MentalStatusExamPersistenceMapper mapper;
    public MentalStatusExamRepositoryAdapter(MentalStatusExamJpaRepository repository, MentalStatusExamPersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    public MentalStatusExam save(MentalStatusExam aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<MentalStatusExam> findById(MentalStatusExamId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<MentalStatusExam> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(MentalStatusExam aggregate) { repository.deleteById(aggregate.id().value()); }

}

