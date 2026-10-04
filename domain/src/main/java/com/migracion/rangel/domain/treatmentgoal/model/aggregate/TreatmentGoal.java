package com.migracion.rangel.domain.treatmentgoal.model.aggregate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.migracion.rangel.domain.treatmentgoal.event.TreatmentGoalRegisteredEvent;
import com.migracion.rangel.domain.treatmentgoal.event.TreatmentGoalUpdatedEvent;
public final class TreatmentGoal extends AggregateRoot {
    private final TreatmentGoalId id;
    private TreatmentPlanId treatmentPlanId;
    private String description;
    private LocalDate targetDate;
    private OffsetDateTime completedAt;
    private String notes;
    private TreatmentGoalStatusId treatmentGoalId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TreatmentGoal(TreatmentGoalId id, TreatmentPlanId treatmentPlanId, String description, LocalDate targetDate, OffsetDateTime completedAt, String notes, TreatmentGoalStatusId treatmentGoalId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(treatmentPlanId, description, targetDate, completedAt, notes, treatmentGoalId);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static TreatmentGoal register(TreatmentPlanId treatmentPlanId, String description, LocalDate targetDate, OffsetDateTime completedAt, String notes, TreatmentGoalStatusId treatmentGoalId) {
        var now = LocalDateTime.now();
        var aggregate = new TreatmentGoal(TreatmentGoalId.generate(), treatmentPlanId, description, targetDate, completedAt, notes, treatmentGoalId, now, now);
        aggregate.recordEvent(new TreatmentGoalRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static TreatmentGoal restore(TreatmentGoalId id, TreatmentPlanId treatmentPlanId, String description, LocalDate targetDate, OffsetDateTime completedAt, String notes, TreatmentGoalStatusId treatmentGoalId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new TreatmentGoal(id, treatmentPlanId, description, targetDate, completedAt, notes, treatmentGoalId, createdAt, updatedAt);
    }
    public void update(TreatmentPlanId treatmentPlanId, String description, LocalDate targetDate, OffsetDateTime completedAt, String notes, TreatmentGoalStatusId treatmentGoalId) {
        setDetails(treatmentPlanId, description, targetDate, completedAt, notes, treatmentGoalId);
        var now = LocalDateTime.now();
        updatedAt = now;
        recordEvent(new TreatmentGoalUpdatedEvent(id, now));
    }
    private void setDetails(TreatmentPlanId treatmentPlanId, String description, LocalDate targetDate, OffsetDateTime completedAt, String notes, TreatmentGoalStatusId treatmentGoalId) {
        // Validar todos los valores antes de modificar el agregado.
        Objects.requireNonNull(treatmentPlanId, "treatmentPlanId es obligatorio");
        Objects.requireNonNull(description, "description es obligatorio");
        Objects.requireNonNull(targetDate, "targetDate es obligatorio");
        Objects.requireNonNull(completedAt, "completedAt es obligatorio");
        Objects.requireNonNull(notes, "notes es obligatorio");
        Objects.requireNonNull(treatmentGoalId, "treatmentGoalId es obligatorio");
        this.treatmentPlanId = treatmentPlanId;
        this.description = description;
        this.targetDate = targetDate;
        this.completedAt = completedAt;
        this.notes = notes;
        this.treatmentGoalId = treatmentGoalId;
    }
    public TreatmentGoalId id() { return id; }
    public TreatmentPlanId treatmentPlanId() { return treatmentPlanId; }
    public String description() { return description; }
    public LocalDate targetDate() { return targetDate; }
    public OffsetDateTime completedAt() { return completedAt; }
    public String notes() { return notes; }
    public TreatmentGoalStatusId treatmentGoalId() { return treatmentGoalId; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}

