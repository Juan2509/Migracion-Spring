package com.migracion.rangel.infrastructure.clinicalnote.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.clinicalnote.model.aggregate.ClinicalNote;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
import com.migracion.rangel.domain.clinicalnote.port.repository.ClinicalNoteRepository;
import com.migracion.rangel.infrastructure.clinicalnote.adapters.out.persistence.mappers.ClinicalNotePersistenceMapper;
public class ClinicalNoteRepositoryAdapter implements ClinicalNoteRepository {
    private final ClinicalNoteJpaRepository repository;
    private final ClinicalNotePersistenceMapper mapper;
    public ClinicalNoteRepositoryAdapter(ClinicalNoteJpaRepository repository, ClinicalNotePersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    public ClinicalNote save(ClinicalNote aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ClinicalNote> findById(ClinicalNoteId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ClinicalNote> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ClinicalNote aggregate) { repository.deleteById(aggregate.id().value()); }
}

