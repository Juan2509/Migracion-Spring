package com.migracion.rangel.application.stateregion.usecase;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.application.stateregion.dto.StateRegionResponse;
import com.migracion.rangel.application.stateregion.exception.StateRegionNotFoundApplicationException;


public class GetStateRegionByIdUseCase {
    private final StateRegionRepository repository;

    public GetStateRegionByIdUseCase(StateRegionRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public StateRegionResponse execute(StateRegionId id) { return StateRegionResponse.from(repository.findById(id).orElseThrow(() -> new StateRegionNotFoundApplicationException(id))); }
}
