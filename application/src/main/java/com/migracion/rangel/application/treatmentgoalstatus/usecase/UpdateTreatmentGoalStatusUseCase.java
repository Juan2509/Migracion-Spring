package com.migracion.rangel.application.treatmentgoalstatus.usecase;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.application.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.migracion.rangel.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.exception.DuplicateTreatmentGoalStatusApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.command.UpdateTreatmentGoalStatusCommand;
public class UpdateTreatmentGoalStatusUseCase {
    private final TreatmentGoalStatusRepository repository;
    public UpdateTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public TreatmentGoalStatusResponse execute(UpdateTreatmentGoalStatusCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new TreatmentGoalStatusNotFoundApplicationException(id));
        if (repository.existsByCodeAndIdNot(command.code(), id)) { throw new DuplicateTreatmentGoalStatusApplicationException("code"); }
        if (repository.existsByNameAndIdNot(command.name(), id)) { throw new DuplicateTreatmentGoalStatusApplicationException("name"); }
        aggregate.update(command.code(), command.name(), command.active());
        return TreatmentGoalStatusResponse.from(repository.save(aggregate));
    }
}



