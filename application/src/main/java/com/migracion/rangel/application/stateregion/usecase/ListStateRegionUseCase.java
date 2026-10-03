package com.migracion.rangel.application.stateregion.usecase;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.application.stateregion.dto.StateRegionResponse;
import com.migracion.rangel.application.stateregion.exception.StateRegionNotFoundApplicationException;

import java.util.List;
public class ListStateRegionUseCase {
    private final StateRegionRepository repository;

    public ListStateRegionUseCase(StateRegionRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public List<StateRegionResponse> execute() { return repository.findAll().stream().map(StateRegionResponse::from).toList(); }
}
