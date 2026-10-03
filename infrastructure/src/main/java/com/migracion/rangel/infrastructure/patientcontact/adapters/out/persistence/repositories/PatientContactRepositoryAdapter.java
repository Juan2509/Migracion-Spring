package com.migracion.rangel.infrastructure.patientcontact.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.patientcontact.model.aggregate.PatientContact;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
import com.migracion.rangel.domain.patientcontact.port.repository.PatientContactRepository;
import com.migracion.rangel.infrastructure.patientcontact.adapters.out.persistence.mappers.PatientContactPersistenceMapper;
public class PatientContactRepositoryAdapter implements PatientContactRepository {
    private final PatientContactJpaRepository repository;
    private final PatientContactPersistenceMapper mapper;
    public PatientContactRepositoryAdapter(PatientContactJpaRepository repository, PatientContactPersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    public PatientContact save(PatientContact aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<PatientContact> findById(PatientContactId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<PatientContact> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(PatientContact aggregate) { repository.deleteById(aggregate.id().value()); }
}
