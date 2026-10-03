package com.migracion.rangel.application.encounterstatus.usecase;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.application.encounterstatus.dto.EncounterStatusResponse;
import com.migracion.rangel.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.migracion.rangel.application.encounterstatus.exception.DuplicateEncounterStatusApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.encounterstatus.event.EncounterStatusDeletedEvent;
public class DeleteEncounterStatusUseCase {
    private final EncounterStatusRepository repository;
    public DeleteEncounterStatusUseCase(EncounterStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterStatusDeletedEvent execute(EncounterStatusId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new EncounterStatusNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new EncounterStatusDeletedEvent(id, LocalDateTime.now());
    }
}

