package com.migracion.rangel.application.treatmentgoalstatus.usecase;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.application.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.migracion.rangel.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.exception.DuplicateTreatmentGoalStatusApplicationException;

public class GetTreatmentGoalStatusByIdUseCase {
    private final TreatmentGoalStatusRepository repository;
    public GetTreatmentGoalStatusByIdUseCase(TreatmentGoalStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public TreatmentGoalStatusResponse execute(TreatmentGoalStatusId id) { return TreatmentGoalStatusResponse.from(repository.findById(id).orElseThrow(() -> new TreatmentGoalStatusNotFoundApplicationException(id))); }
}



