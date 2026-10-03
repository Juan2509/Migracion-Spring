package com.migracion.rangel.application.encounterstatus.usecase;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.application.encounterstatus.dto.EncounterStatusResponse;
import com.migracion.rangel.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.migracion.rangel.application.encounterstatus.exception.DuplicateEncounterStatusApplicationException;
import com.migracion.rangel.application.encounterstatus.command.UpdateEncounterStatusCommand;
public class UpdateEncounterStatusUseCase {
    private final EncounterStatusRepository repository;
    public UpdateEncounterStatusUseCase(EncounterStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterStatusResponse execute(UpdateEncounterStatusCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new EncounterStatusNotFoundApplicationException(id));
        if (repository.existsByCodeAndIdNot(command.code(), id)) { throw new DuplicateEncounterStatusApplicationException("code"); }
        if (repository.existsByNameAndIdNot(command.name(), id)) { throw new DuplicateEncounterStatusApplicationException("name"); }
        aggregate.update(command.code(), command.name(), command.active());
        return EncounterStatusResponse.from(repository.save(aggregate));
    }
}

