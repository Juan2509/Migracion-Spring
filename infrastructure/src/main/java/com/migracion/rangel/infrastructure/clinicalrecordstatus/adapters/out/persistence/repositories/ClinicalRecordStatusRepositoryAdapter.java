package com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.out.persistence.mappers.ClinicalRecordStatusPersistenceMapper;
public class ClinicalRecordStatusRepositoryAdapter implements ClinicalRecordStatusRepository {
    private final ClinicalRecordStatusJpaRepository repository;
    private final ClinicalRecordStatusPersistenceMapper mapper;
    public ClinicalRecordStatusRepositoryAdapter(ClinicalRecordStatusJpaRepository repository, ClinicalRecordStatusPersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    public ClinicalRecordStatus save(ClinicalRecordStatus aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ClinicalRecordStatus> findById(ClinicalRecordStatusId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ClinicalRecordStatus> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ClinicalRecordStatus aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String code) { return repository.existsByCode(code); }
    public boolean existsByCodeAndIdNot(String code, ClinicalRecordStatusId id) { return repository.existsByCodeAndIdNot(code, id.value()); }
    public boolean existsByName(String name) { return repository.existsByName(name); }
    public boolean existsByNameAndIdNot(String name, ClinicalRecordStatusId id) { return repository.existsByNameAndIdNot(name, id.value()); }
}
