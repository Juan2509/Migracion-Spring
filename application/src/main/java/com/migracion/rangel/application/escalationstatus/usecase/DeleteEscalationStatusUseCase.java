package com.migracion.rangel.application.escalationstatus.usecase;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.application.escalationstatus.dto.EscalationStatusResponse;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.escalationstatus.event.EscalationStatusDeletedEvent;
public class DeleteEscalationStatusUseCase {
    private final EscalationStatusRepository repository;
    public DeleteEscalationStatusUseCase(EscalationStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EscalationStatusDeletedEvent execute(EscalationStatusId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new EscalationStatusNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new EscalationStatusDeletedEvent(id, LocalDateTime.now());
    }
}
