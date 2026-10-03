package com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.professionalstudy.model.aggregate.ProfessionalStudy;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.migracion.rangel.domain.professionalstudy.port.repository.ProfessionalStudyRepository;
import com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.mappers.ProfessionalStudyPersistenceMapper;
public class ProfessionalStudyRepositoryAdapter implements ProfessionalStudyRepository {
    private final ProfessionalStudyJpaRepository repository;
    private final ProfessionalStudyPersistenceMapper mapper;
    public ProfessionalStudyRepositoryAdapter(ProfessionalStudyJpaRepository repository, ProfessionalStudyPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ProfessionalStudy save(ProfessionalStudy aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ProfessionalStudy> findById(ProfessionalStudyId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ProfessionalStudy> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ProfessionalStudy aggregate) { repository.deleteById(aggregate.id().value()); }
}
