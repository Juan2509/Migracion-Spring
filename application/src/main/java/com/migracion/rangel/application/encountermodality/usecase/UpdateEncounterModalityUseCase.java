package com.migracion.rangel.application.encountermodality.usecase;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.application.encountermodality.dto.EncounterModalityResponse;
import com.migracion.rangel.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.migracion.rangel.application.encountermodality.exception.DuplicateEncounterModalityApplicationException;
import com.migracion.rangel.application.encountermodality.command.UpdateEncounterModalityCommand;
public class UpdateEncounterModalityUseCase {
    private final EncounterModalityRepository repository;
    public UpdateEncounterModalityUseCase(EncounterModalityRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterModalityResponse execute(UpdateEncounterModalityCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new EncounterModalityNotFoundApplicationException(id));
        if (repository.existsByCodeAndIdNot(command.code(), id)) { throw new DuplicateEncounterModalityApplicationException("code"); }
        if (repository.existsByNameAndIdNot(command.name(), id)) { throw new DuplicateEncounterModalityApplicationException("name"); }
        aggregate.update(command.code(), command.name(), command.active());
        return EncounterModalityResponse.from(repository.save(aggregate));
    }
}

