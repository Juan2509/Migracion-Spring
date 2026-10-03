package com.migracion.rangel.infrastructure.clinicalrecord.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.migracion.rangel.infrastructure.clinicalrecord.adapters.out.persistence.mappers.ClinicalRecordPersistenceMapper;
public class ClinicalRecordRepositoryAdapter implements ClinicalRecordRepository {
    private final ClinicalRecordJpaRepository repository;
    private final ClinicalRecordPersistenceMapper mapper;
    public ClinicalRecordRepositoryAdapter(ClinicalRecordJpaRepository repository, ClinicalRecordPersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    public ClinicalRecord save(ClinicalRecord aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ClinicalRecord> findById(ClinicalRecordId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ClinicalRecord> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ClinicalRecord aggregate) { repository.deleteById(aggregate.id().value()); }

}
