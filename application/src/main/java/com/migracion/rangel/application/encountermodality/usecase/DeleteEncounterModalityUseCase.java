package com.migracion.rangel.application.encountermodality.usecase;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.application.encountermodality.dto.EncounterModalityResponse;
import com.migracion.rangel.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.migracion.rangel.application.encountermodality.exception.DuplicateEncounterModalityApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.encountermodality.event.EncounterModalityDeletedEvent;
public class DeleteEncounterModalityUseCase {
    private final EncounterModalityRepository repository;
    public DeleteEncounterModalityUseCase(EncounterModalityRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterModalityDeletedEvent execute(EncounterModalityId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new EncounterModalityNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new EncounterModalityDeletedEvent(id, LocalDateTime.now());
    }
}

