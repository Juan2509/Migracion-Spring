package com.migracion.rangel.domain.diagnosticsystem.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
public interface DiagnosticSystemRepository {
    DiagnosticSystem save(DiagnosticSystem aggregate);
    Optional<DiagnosticSystem> findById(DiagnosticSystemId id);
    List<DiagnosticSystem> findAll();
    void delete(DiagnosticSystem aggregate);
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, DiagnosticSystemId id);
}

