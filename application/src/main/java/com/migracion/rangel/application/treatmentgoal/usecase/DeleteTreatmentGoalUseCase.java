package com.migracion.rangel.application.treatmentgoal.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.treatmentgoal.port.repository.TreatmentGoalRepository;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.migracion.rangel.domain.treatmentgoal.event.TreatmentGoalDeletedEvent;
import com.migracion.rangel.application.treatmentgoal.dto.TreatmentGoalResponse;
import com.migracion.rangel.application.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
public class DeleteTreatmentGoalUseCase {
    private final TreatmentGoalRepository repository;
    public DeleteTreatmentGoalUseCase(TreatmentGoalRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public TreatmentGoalDeletedEvent execute(TreatmentGoalId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new TreatmentGoalNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new TreatmentGoalDeletedEvent(id, LocalDateTime.now());
    }
}

