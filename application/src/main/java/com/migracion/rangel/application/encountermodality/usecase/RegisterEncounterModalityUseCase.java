package com.migracion.rangel.application.encountermodality.usecase;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.application.encountermodality.dto.EncounterModalityResponse;
import com.migracion.rangel.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.migracion.rangel.application.encountermodality.exception.DuplicateEncounterModalityApplicationException;
import com.migracion.rangel.application.encountermodality.command.RegisterEncounterModalityCommand;
import com.migracion.rangel.domain.encountermodality.model.aggregate.EncounterModality;
public class RegisterEncounterModalityUseCase {
    private final EncounterModalityRepository repository;
    public RegisterEncounterModalityUseCase(EncounterModalityRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterModalityResponse execute(RegisterEncounterModalityCommand command) {
        var aggregate = EncounterModality.register(command.code(), command.name(), command.active());
        if (repository.existsByCode(command.code())) { throw new DuplicateEncounterModalityApplicationException("code"); }
        if (repository.existsByName(command.name())) { throw new DuplicateEncounterModalityApplicationException("name"); }
        return EncounterModalityResponse.from(repository.save(aggregate));
    }
}

