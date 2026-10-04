package com.migracion.rangel.application.treatmentgoal.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.treatmentgoal.model.aggregate.TreatmentGoal;
import com.migracion.rangel.domain.treatmentgoal.port.repository.TreatmentGoalRepository;
import com.migracion.rangel.application.treatmentgoal.command.RegisterTreatmentGoalCommand;
import com.migracion.rangel.application.treatmentgoal.dto.TreatmentGoalResponse;
import com.migracion.rangel.application.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
import com.migracion.rangel.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import com.migracion.rangel.application.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.migracion.rangel.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
public class RegisterTreatmentGoalUseCase {
    private final TreatmentGoalRepository repository;
    private final TreatmentPlanRepository plans;
    private final TreatmentGoalStatusRepository statuses;
    public RegisterTreatmentGoalUseCase(TreatmentGoalRepository repository, TreatmentPlanRepository plans, TreatmentGoalStatusRepository statuses) {
        this.repository = Objects.requireNonNull(repository);
        this.plans = Objects.requireNonNull(plans);
        this.statuses = Objects.requireNonNull(statuses);
    }
    public TreatmentGoalResponse execute(RegisterTreatmentGoalCommand command) {
        var aggregate = TreatmentGoal.register(command.treatmentPlanId(), command.description(), command.targetDate(), command.completedAt(), command.notes(), command.treatmentGoalId());
        plans.findById(command.treatmentPlanId()).orElseThrow(() -> new TreatmentPlanNotFoundApplicationException(command.treatmentPlanId()));
        statuses.findById(command.treatmentGoalId()).orElseThrow(() -> new TreatmentGoalStatusNotFoundApplicationException(command.treatmentGoalId()));
        return TreatmentGoalResponse.from(repository.save(aggregate));
    }
}

