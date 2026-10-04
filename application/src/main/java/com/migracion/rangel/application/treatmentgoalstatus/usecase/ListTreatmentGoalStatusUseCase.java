package com.migracion.rangel.application.treatmentgoalstatus.usecase;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.application.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.migracion.rangel.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.exception.DuplicateTreatmentGoalStatusApplicationException;
import java.util.List;
public class ListTreatmentGoalStatusUseCase {
    private final TreatmentGoalStatusRepository repository;
    public ListTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<TreatmentGoalStatusResponse> execute() { return repository.findAll().stream().map(TreatmentGoalStatusResponse::from).toList(); }
}



