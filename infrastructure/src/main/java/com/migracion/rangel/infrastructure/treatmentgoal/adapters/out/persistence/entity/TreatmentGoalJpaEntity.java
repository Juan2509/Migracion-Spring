package com.migracion.rangel.infrastructure.treatmentgoal.adapters.out.persistence.entity;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import jakarta.persistence.*;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;
@Entity
@Table(name = "treatment_goals")
public class TreatmentGoalJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "treatment_plan_id", nullable = false)
    private UUID treatmentPlanId;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "completed_at", nullable = false)
    private OffsetDateTime completedAt;

    @Column(name = "notes", nullable = false, columnDefinition = "TEXT")
    private String notes;


    @Column(name = "treatment_goal_id", nullable = false)
    private UUID treatmentGoalId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    public TreatmentGoalJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getTreatmentPlanId() { return treatmentPlanId; }
    public void setTreatmentPlanId(UUID treatmentPlanId) { this.treatmentPlanId = treatmentPlanId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }
    public OffsetDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(OffsetDateTime completedAt) { this.completedAt = completedAt; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public UUID getTreatmentGoalId() { return treatmentGoalId; }
    public void setTreatmentGoalId(UUID treatmentGoalId) { this.treatmentGoalId = treatmentGoalId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

