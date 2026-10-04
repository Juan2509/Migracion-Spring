package com.migracion.rangel.infrastructure.diagnosticsystem.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.migracion.rangel.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;
import com.migracion.rangel.infrastructure.diagnosticsystem.adapters.out.persistence.mappers.DiagnosticSystemPersistenceMapper;
public class DiagnosticSystemRepositoryAdapter implements DiagnosticSystemRepository {
    private final DiagnosticSystemJpaRepository repository;
    private final DiagnosticSystemPersistenceMapper mapper;
    public DiagnosticSystemRepositoryAdapter(DiagnosticSystemJpaRepository repository, DiagnosticSystemPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public DiagnosticSystem save(DiagnosticSystem aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<DiagnosticSystem> findById(DiagnosticSystemId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<DiagnosticSystem> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(DiagnosticSystem aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String value) { return repository.existsByCode(value); }
    public boolean existsByCodeAndIdNot(String value, DiagnosticSystemId id) {
        return repository.existsByCodeAndIdNot(value, id.value());
    }
}

