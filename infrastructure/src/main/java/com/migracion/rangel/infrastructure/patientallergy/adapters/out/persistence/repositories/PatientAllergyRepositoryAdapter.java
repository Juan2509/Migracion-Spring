package com.migracion.rangel.infrastructure.patientallergy.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.patientallergy.model.aggregate.PatientAllergy;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
import com.migracion.rangel.domain.patientallergy.port.repository.PatientAllergyRepository;
import com.migracion.rangel.infrastructure.patientallergy.adapters.out.persistence.mappers.PatientAllergyPersistenceMapper;
public class PatientAllergyRepositoryAdapter implements PatientAllergyRepository {
    private final PatientAllergyJpaRepository repository;
    private final PatientAllergyPersistenceMapper mapper;
    public PatientAllergyRepositoryAdapter(PatientAllergyJpaRepository repository, PatientAllergyPersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    public PatientAllergy save(PatientAllergy aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<PatientAllergy> findById(PatientAllergyId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<PatientAllergy> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(PatientAllergy aggregate) { repository.deleteById(aggregate.id().value()); }
}
