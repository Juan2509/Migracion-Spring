package com.migracion.rangel.application.diagnosticsystem.usecase;
import com.migracion.rangel.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.migracion.rangel.application.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.migracion.rangel.application.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import com.migracion.rangel.application.diagnosticsystem.exception.DuplicateDiagnosticSystemApplicationException;

public class GetDiagnosticSystemByIdUseCase {
    private final DiagnosticSystemRepository repository;
    public GetDiagnosticSystemByIdUseCase(DiagnosticSystemRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public DiagnosticSystemResponse execute(DiagnosticSystemId id) { return DiagnosticSystemResponse.from(repository.findById(id).orElseThrow(() -> new DiagnosticSystemNotFoundApplicationException(id))); }
}

