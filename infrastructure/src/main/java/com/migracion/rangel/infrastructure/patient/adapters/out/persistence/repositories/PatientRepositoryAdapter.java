package com.migracion.rangel.infrastructure.patient.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.patient.model.aggregate.Patient;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.infrastructure.patient.adapters.out.persistence.mappers.PatientPersistenceMapper;
public class PatientRepositoryAdapter implements PatientRepository {
    private final PatientJpaRepository repository;
    private final PatientPersistenceMapper mapper;
    public PatientRepositoryAdapter(PatientJpaRepository repository, PatientPersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    public Patient save(Patient patient) { return mapper.toDomain(repository.save(mapper.toJpa(patient))); }
    public Optional<Patient> findById(PatientId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<Patient> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(Patient patient) { repository.deleteById(patient.id().value()); }
    public boolean existsByEmail(String email) { return repository.existsByEmail(email); }
    public boolean existsByEmailAndIdNot(String email, PatientId id) { return repository.existsByEmailAndIdNot(email, id.value()); }
}
