package com.migracion.rangel.application.diagnosticsystem.usecase;
import com.migracion.rangel.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.migracion.rangel.application.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.migracion.rangel.application.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import com.migracion.rangel.application.diagnosticsystem.exception.DuplicateDiagnosticSystemApplicationException;
import java.util.List;
public class ListDiagnosticSystemUseCase {
    private final DiagnosticSystemRepository repository;
    public ListDiagnosticSystemUseCase(DiagnosticSystemRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<DiagnosticSystemResponse> execute() { return repository.findAll().stream().map(DiagnosticSystemResponse::from).toList(); }
}

