package com.migracion.rangel.application.encountertype.usecase;
import com.migracion.rangel.domain.encountertype.port.repository.EncounterTypeRepository;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.application.encountertype.dto.EncounterTypeResponse;
import com.migracion.rangel.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.migracion.rangel.application.encountertype.exception.DuplicateEncounterTypeApplicationException;
import com.migracion.rangel.application.encountertype.command.RegisterEncounterTypeCommand;
import com.migracion.rangel.domain.encountertype.model.aggregate.EncounterType;
public class RegisterEncounterTypeUseCase {
    private final EncounterTypeRepository repository;
    public RegisterEncounterTypeUseCase(EncounterTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterTypeResponse execute(RegisterEncounterTypeCommand command) {
        var aggregate = EncounterType.register(command.code(), command.name(), command.active());
        if (repository.existsByCode(command.code())) { throw new DuplicateEncounterTypeApplicationException("code"); }
        if (repository.existsByName(command.name())) { throw new DuplicateEncounterTypeApplicationException("name"); }
        return EncounterTypeResponse.from(repository.save(aggregate));
    }
}

