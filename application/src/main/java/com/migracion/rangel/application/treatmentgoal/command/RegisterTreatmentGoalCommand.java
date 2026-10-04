package com.migracion.rangel.application.treatmentgoal.command;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
public record RegisterTreatmentGoalCommand(TreatmentPlanId treatmentPlanId, String description, LocalDate targetDate, OffsetDateTime completedAt, String notes, TreatmentGoalStatusId treatmentGoalId) {}


