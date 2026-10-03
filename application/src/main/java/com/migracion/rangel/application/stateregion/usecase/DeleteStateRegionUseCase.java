package com.migracion.rangel.application.stateregion.usecase;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.application.stateregion.dto.StateRegionResponse;
import com.migracion.rangel.application.stateregion.exception.StateRegionNotFoundApplicationException;

import java.time.LocalDateTime;
import com.migracion.rangel.domain.stateregion.event.StateRegionDeletedEvent;
public class DeleteStateRegionUseCase {
    private final StateRegionRepository repository;

    public DeleteStateRegionUseCase(StateRegionRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public StateRegionDeletedEvent execute(StateRegionId id) {
        var region = repository.findById(id).orElseThrow(() -> new StateRegionNotFoundApplicationException(id));
        repository.delete(region);
        return new StateRegionDeletedEvent(id, LocalDateTime.now());
    }
}
