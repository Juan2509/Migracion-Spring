package com.migracion.rangel.infrastructure.professionaltype.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.professionaltype.model.aggregate.ProfessionalType;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import com.migracion.rangel.infrastructure.professionaltype.adapters.out.persistence.mappers.ProfessionalTypePersistenceMapper;
public class ProfessionalTypeRepositoryAdapter implements ProfessionalTypeRepository {
    private final ProfessionalTypeJpaRepository repository;
    private final ProfessionalTypePersistenceMapper mapper;
    public ProfessionalTypeRepositoryAdapter(ProfessionalTypeJpaRepository repository, ProfessionalTypePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ProfessionalType save(ProfessionalType aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ProfessionalType> findById(ProfessionalTypeId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ProfessionalType> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ProfessionalType aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByName(String value) { return repository.existsByName(value); }
    public boolean existsByNameAndIdNot(String value, ProfessionalTypeId id) {
        return repository.existsByNameAndIdNot(value, id.value());
    }
}
