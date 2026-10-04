package com.migracion.rangel.application.diagnosticsystem.usecase;
import com.migracion.rangel.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.migracion.rangel.application.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.migracion.rangel.application.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import com.migracion.rangel.application.diagnosticsystem.exception.DuplicateDiagnosticSystemApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.diagnosticsystem.event.DiagnosticSystemDeletedEvent;
public class DeleteDiagnosticSystemUseCase {
    private final DiagnosticSystemRepository repository;
    public DeleteDiagnosticSystemUseCase(DiagnosticSystemRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public DiagnosticSystemDeletedEvent execute(DiagnosticSystemId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new DiagnosticSystemNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new DiagnosticSystemDeletedEvent(id, LocalDateTime.now());
    }
}

