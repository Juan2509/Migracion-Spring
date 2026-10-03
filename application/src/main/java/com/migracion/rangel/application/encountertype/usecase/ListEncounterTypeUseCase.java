package com.migracion.rangel.application.encountertype.usecase;
import com.migracion.rangel.domain.encountertype.port.repository.EncounterTypeRepository;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.application.encountertype.dto.EncounterTypeResponse;
import com.migracion.rangel.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.migracion.rangel.application.encountertype.exception.DuplicateEncounterTypeApplicationException;
import java.util.List;
public class ListEncounterTypeUseCase {
    private final EncounterTypeRepository repository;
    public ListEncounterTypeUseCase(EncounterTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<EncounterTypeResponse> execute() { return repository.findAll().stream().map(EncounterTypeResponse::from).toList(); }
}

