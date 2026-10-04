package com.migracion.rangel.application.treatmentgoalstatus.usecase;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.application.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.migracion.rangel.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.exception.DuplicateTreatmentGoalStatusApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.command.RegisterTreatmentGoalStatusCommand;
import com.migracion.rangel.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
public class RegisterTreatmentGoalStatusUseCase {
    private final TreatmentGoalStatusRepository repository;
    public RegisterTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public TreatmentGoalStatusResponse execute(RegisterTreatmentGoalStatusCommand command) {
        var aggregate = TreatmentGoalStatus.register(command.code(), command.name(), command.active());
        if (repository.existsByCode(command.code())) { throw new DuplicateTreatmentGoalStatusApplicationException("code"); }
        if (repository.existsByName(command.name())) { throw new DuplicateTreatmentGoalStatusApplicationException("name"); }
        return TreatmentGoalStatusResponse.from(repository.save(aggregate));
    }
}



