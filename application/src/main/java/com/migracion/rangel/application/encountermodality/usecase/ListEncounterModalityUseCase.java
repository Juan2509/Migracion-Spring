package com.migracion.rangel.application.encountermodality.usecase;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.application.encountermodality.dto.EncounterModalityResponse;
import com.migracion.rangel.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.migracion.rangel.application.encountermodality.exception.DuplicateEncounterModalityApplicationException;
import java.util.List;
public class ListEncounterModalityUseCase {
    private final EncounterModalityRepository repository;
    public ListEncounterModalityUseCase(EncounterModalityRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<EncounterModalityResponse> execute() { return repository.findAll().stream().map(EncounterModalityResponse::from).toList(); }
}

