package com.migracion.rangel.application.treatmentgoalstatus.usecase;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.application.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.migracion.rangel.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.exception.DuplicateTreatmentGoalStatusApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.treatmentgoalstatus.event.TreatmentGoalStatusDeletedEvent;
public class DeleteTreatmentGoalStatusUseCase {
    private final TreatmentGoalStatusRepository repository;
    public DeleteTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public TreatmentGoalStatusDeletedEvent execute(TreatmentGoalStatusId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new TreatmentGoalStatusNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new TreatmentGoalStatusDeletedEvent(id, LocalDateTime.now());
    }
}



