package com.migracion.rangel.application.diagnosticsystem.usecase;
import com.migracion.rangel.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.migracion.rangel.application.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.migracion.rangel.application.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import com.migracion.rangel.application.diagnosticsystem.exception.DuplicateDiagnosticSystemApplicationException;
import com.migracion.rangel.application.diagnosticsystem.command.RegisterDiagnosticSystemCommand;
import com.migracion.rangel.domain.diagnosticsystem.model.aggregate.DiagnosticSystem;
public class RegisterDiagnosticSystemUseCase {
    private final DiagnosticSystemRepository repository;
    public RegisterDiagnosticSystemUseCase(DiagnosticSystemRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public DiagnosticSystemResponse execute(RegisterDiagnosticSystemCommand command) {
        var aggregate = DiagnosticSystem.register(command.code(), command.name(), command.active(), command.version());
        if (repository.existsByCode(command.code())) { throw new DuplicateDiagnosticSystemApplicationException(); }
        return DiagnosticSystemResponse.from(repository.save(aggregate));
    }
}

