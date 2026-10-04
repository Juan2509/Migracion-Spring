package com.migracion.rangel.application.treatmentgoal.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.treatmentgoal.port.repository.TreatmentGoalRepository;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.migracion.rangel.domain.treatmentgoal.event.TreatmentGoalDeletedEvent;
import com.migracion.rangel.application.treatmentgoal.dto.TreatmentGoalResponse;
import com.migracion.rangel.application.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
public class ListTreatmentGoalUseCase {
    private final TreatmentGoalRepository repository;
    public ListTreatmentGoalUseCase(TreatmentGoalRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<TreatmentGoalResponse> execute() {
        return repository.findAll().stream().map(TreatmentGoalResponse::from).toList();
    }
}

