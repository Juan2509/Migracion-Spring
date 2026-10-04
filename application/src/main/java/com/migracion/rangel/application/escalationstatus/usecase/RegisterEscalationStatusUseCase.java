package com.migracion.rangel.application.escalationstatus.usecase;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.application.escalationstatus.dto.EscalationStatusResponse;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import com.migracion.rangel.application.escalationstatus.command.RegisterEscalationStatusCommand;
import com.migracion.rangel.domain.escalationstatus.model.aggregate.EscalationStatus;
public class RegisterEscalationStatusUseCase {
    private final EscalationStatusRepository repository;
    public RegisterEscalationStatusUseCase(EscalationStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EscalationStatusResponse execute(RegisterEscalationStatusCommand command) {
        var aggregate = EscalationStatus.register(command.nameStatus());
        return EscalationStatusResponse.from(repository.save(aggregate));
    }
}
