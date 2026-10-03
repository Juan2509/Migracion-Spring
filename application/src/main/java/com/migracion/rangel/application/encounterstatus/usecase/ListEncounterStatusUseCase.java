package com.migracion.rangel.application.encounterstatus.usecase;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.application.encounterstatus.dto.EncounterStatusResponse;
import com.migracion.rangel.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.migracion.rangel.application.encounterstatus.exception.DuplicateEncounterStatusApplicationException;
import java.util.List;
public class ListEncounterStatusUseCase {
    private final EncounterStatusRepository repository;
    public ListEncounterStatusUseCase(EncounterStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<EncounterStatusResponse> execute() { return repository.findAll().stream().map(EncounterStatusResponse::from).toList(); }
}

