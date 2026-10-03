package com.migracion.rangel.application.encounterstatus.usecase;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.application.encounterstatus.dto.EncounterStatusResponse;
import com.migracion.rangel.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.migracion.rangel.application.encounterstatus.exception.DuplicateEncounterStatusApplicationException;

public class GetEncounterStatusByIdUseCase {
    private final EncounterStatusRepository repository;
    public GetEncounterStatusByIdUseCase(EncounterStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EncounterStatusResponse execute(EncounterStatusId id) { return EncounterStatusResponse.from(repository.findById(id).orElseThrow(() -> new EncounterStatusNotFoundApplicationException(id))); }
}

