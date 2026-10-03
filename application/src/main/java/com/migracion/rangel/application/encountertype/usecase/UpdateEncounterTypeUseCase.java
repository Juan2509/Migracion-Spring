package com.migracion.rangel.application.encountertype.usecase;
import com.migracion.rangel.domain.encountertype.port.repository.EncounterTypeRepository;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.application.encountertype.dto.EncounterTypeResponse;
import com.migracion.rangel.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.migracion.rangel.application.encountertype.exception.DuplicateEncounterTypeApplicationException;
import com.migracion.rangel.application.encountertype.command.UpdateEncounterTypeCommand;
public class UpdateEncounterTypeUseCase {
    private final EncounterTypeRepository repository;
    public UpdateEncounterTypeUseCase(EncounterTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterTypeResponse execute(UpdateEncounterTypeCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new EncounterTypeNotFoundApplicationException(id));
        if (repository.existsByCodeAndIdNot(command.code(), id)) { throw new DuplicateEncounterTypeApplicationException("code"); }
        if (repository.existsByNameAndIdNot(command.name(), id)) { throw new DuplicateEncounterTypeApplicationException("name"); }
        aggregate.update(command.code(), command.name(), command.active());
        return EncounterTypeResponse.from(repository.save(aggregate));
    }
}

