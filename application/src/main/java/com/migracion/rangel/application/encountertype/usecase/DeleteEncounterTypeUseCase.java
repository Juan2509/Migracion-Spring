package com.migracion.rangel.application.encountertype.usecase;
import com.migracion.rangel.domain.encountertype.port.repository.EncounterTypeRepository;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.application.encountertype.dto.EncounterTypeResponse;
import com.migracion.rangel.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.migracion.rangel.application.encountertype.exception.DuplicateEncounterTypeApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.encountertype.event.EncounterTypeDeletedEvent;
public class DeleteEncounterTypeUseCase {
    private final EncounterTypeRepository repository;
    public DeleteEncounterTypeUseCase(EncounterTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterTypeDeletedEvent execute(EncounterTypeId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new EncounterTypeNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new EncounterTypeDeletedEvent(id, LocalDateTime.now());
    }
}

