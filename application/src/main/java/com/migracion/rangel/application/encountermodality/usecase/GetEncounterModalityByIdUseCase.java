package com.migracion.rangel.application.encountermodality.usecase;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.application.encountermodality.dto.EncounterModalityResponse;
import com.migracion.rangel.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.migracion.rangel.application.encountermodality.exception.DuplicateEncounterModalityApplicationException;

public class GetEncounterModalityByIdUseCase {
    private final EncounterModalityRepository repository;
    public GetEncounterModalityByIdUseCase(EncounterModalityRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterModalityResponse execute(EncounterModalityId id) { return EncounterModalityResponse.from(repository.findById(id).orElseThrow(() -> new EncounterModalityNotFoundApplicationException(id))); }
}

