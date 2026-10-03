package com.migracion.rangel.application.encountertype.usecase;
import com.migracion.rangel.domain.encountertype.port.repository.EncounterTypeRepository;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.application.encountertype.dto.EncounterTypeResponse;
import com.migracion.rangel.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.migracion.rangel.application.encountertype.exception.DuplicateEncounterTypeApplicationException;

public class GetEncounterTypeByIdUseCase {
    private final EncounterTypeRepository repository;
    public GetEncounterTypeByIdUseCase(EncounterTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterTypeResponse execute(EncounterTypeId id) { return EncounterTypeResponse.from(repository.findById(id).orElseThrow(() -> new EncounterTypeNotFoundApplicationException(id))); }
}

