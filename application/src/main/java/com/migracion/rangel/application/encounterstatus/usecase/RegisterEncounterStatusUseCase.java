package com.migracion.rangel.application.encounterstatus.usecase;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.application.encounterstatus.dto.EncounterStatusResponse;
import com.migracion.rangel.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.migracion.rangel.application.encounterstatus.exception.DuplicateEncounterStatusApplicationException;
import com.migracion.rangel.application.encounterstatus.command.RegisterEncounterStatusCommand;
import com.migracion.rangel.domain.encounterstatus.model.aggregate.EncounterStatus;
public class RegisterEncounterStatusUseCase {
    private final EncounterStatusRepository repository;
    public RegisterEncounterStatusUseCase(EncounterStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterStatusResponse execute(RegisterEncounterStatusCommand command) {
        var aggregate = EncounterStatus.register(command.code(), command.name(), command.active());
        if (repository.existsByCode(command.code())) { throw new DuplicateEncounterStatusApplicationException("code"); }
        if (repository.existsByName(command.name())) { throw new DuplicateEncounterStatusApplicationException("name"); }
        return EncounterStatusResponse.from(repository.save(aggregate));
    }
}

