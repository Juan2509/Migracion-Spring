package com.migracion.rangel.application.escalationstatus.usecase;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.application.escalationstatus.dto.EscalationStatusResponse;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import com.migracion.rangel.application.escalationstatus.command.UpdateEscalationStatusCommand;
public class UpdateEscalationStatusUseCase {
    private final EscalationStatusRepository repository;
    public UpdateEscalationStatusUseCase(EscalationStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EscalationStatusResponse execute(UpdateEscalationStatusCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new EscalationStatusNotFoundApplicationException(id));
        aggregate.update(command.nameStatus());
        return EscalationStatusResponse.from(repository.save(aggregate));
    }
}
