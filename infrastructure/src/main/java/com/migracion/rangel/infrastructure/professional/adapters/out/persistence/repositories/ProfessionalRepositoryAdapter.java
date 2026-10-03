package com.migracion.rangel.infrastructure.professional.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.infrastructure.professional.adapters.out.persistence.mappers.ProfessionalPersistenceMapper;

public class ProfessionalRepositoryAdapter implements ProfessionalRepository {
    private final ProfessionalJpaRepository repository;
    private final ProfessionalPersistenceMapper mapper;
    public ProfessionalRepositoryAdapter(ProfessionalJpaRepository repository, ProfessionalPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public Professional save(Professional professional) { return mapper.toDomain(repository.save(mapper.toJpa(professional))); }
    public Optional<Professional> findById(ProfessionalId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<Professional> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(Professional professional) { repository.deleteById(professional.id().value()); }
    public boolean existsByDocumentNumber(String value) { return repository.existsByDocumentNumber(value); }
    public boolean existsByDocumentNumberAndIdNot(String value, ProfessionalId id) {
        return repository.existsByDocumentNumberAndIdNot(value, id.value());
    }
    public boolean existsByFirstName(String value) { return repository.existsByFirstName(value); }
    public boolean existsByFirstNameAndIdNot(String value, ProfessionalId id) {
        return repository.existsByFirstNameAndIdNot(value, id.value());
    }
    public boolean existsByLastName(String value) { return repository.existsByLastName(value); }
    public boolean existsByLastNameAndIdNot(String value, ProfessionalId id) {
        return repository.existsByLastNameAndIdNot(value, id.value());
    }
    public boolean existsByLicenseNumber(String value) { return repository.existsByLicenseNumber(value); }
    public boolean existsByLicenseNumberAndIdNot(String value, ProfessionalId id) {
        return repository.existsByLicenseNumberAndIdNot(value, id.value());
    }
}
