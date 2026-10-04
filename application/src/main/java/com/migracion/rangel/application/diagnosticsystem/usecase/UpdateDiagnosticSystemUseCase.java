package com.migracion.rangel.application.diagnosticsystem.usecase;
import com.migracion.rangel.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.migracion.rangel.application.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.migracion.rangel.application.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import com.migracion.rangel.application.diagnosticsystem.exception.DuplicateDiagnosticSystemApplicationException;
import com.migracion.rangel.application.diagnosticsystem.command.UpdateDiagnosticSystemCommand;
public class UpdateDiagnosticSystemUseCase {
    private final DiagnosticSystemRepository repository;
    public UpdateDiagnosticSystemUseCase(DiagnosticSystemRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public DiagnosticSystemResponse execute(UpdateDiagnosticSystemCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new DiagnosticSystemNotFoundApplicationException(id));
        if (repository.existsByCodeAndIdNot(command.code(), id)) { throw new DuplicateDiagnosticSystemApplicationException(); }
        aggregate.update(command.code(), command.name(), command.active(), command.version());
        return DiagnosticSystemResponse.from(repository.save(aggregate));
    }
}

