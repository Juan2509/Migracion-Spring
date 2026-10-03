package com.migracion.rangel.application.encounter.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.encounter.event.EncounterDeletedEvent;
import com.migracion.rangel.application.encounter.dto.EncounterResponse;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
public class ListEncounterUseCase {
    private final EncounterRepository repository;
    public ListEncounterUseCase(EncounterRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<EncounterResponse> execute() {
        return repository.findAll().stream().map(EncounterResponse::from).toList();
    }
}

