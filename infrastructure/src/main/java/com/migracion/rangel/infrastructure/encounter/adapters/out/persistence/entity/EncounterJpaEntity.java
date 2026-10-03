package com.migracion.rangel.infrastructure.encounter.adapters.out.persistence.entity;
import java.util.UUID;
import java.time.OffsetDateTime;
import jakarta.persistence.*;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;
@Entity
@Table(name = "encounters")
public class EncounterJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "clinical_record_id", nullable = false)
    private UUID clinicalRecordId;

    @Column(name = "professional_id", nullable = false)
    private UUID professionalId;

    @Column(name = "encounter_type_id", nullable = false)
    private UUID encounterTypeId;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "started_at", nullable = false)
    private OffsetDateTime startedAt;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "ended_at", nullable = false)
    private OffsetDateTime endedAt;

    @Column(name = "reason_for_visit", nullable = false, columnDefinition = "TEXT")
    private String reasonForVisit;

    @Column(name = "current_condition", nullable = false, columnDefinition = "TEXT")
    private String currentCondition;

    @Column(name = "modality_id", nullable = false)
    private UUID modalityId;

    @Column(name = "status_id", nullable = false)
    private UUID statusId;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;
    public EncounterJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getClinicalRecordId() { return clinicalRecordId; }
    public void setClinicalRecordId(UUID clinicalRecordId) { this.clinicalRecordId = clinicalRecordId; }
    public UUID getProfessionalId() { return professionalId; }
    public void setProfessionalId(UUID professionalId) { this.professionalId = professionalId; }
    public UUID getEncounterTypeId() { return encounterTypeId; }
    public void setEncounterTypeId(UUID encounterTypeId) { this.encounterTypeId = encounterTypeId; }
    public OffsetDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(OffsetDateTime startedAt) { this.startedAt = startedAt; }
    public OffsetDateTime getEndedAt() { return endedAt; }
    public void setEndedAt(OffsetDateTime endedAt) { this.endedAt = endedAt; }
    public String getReasonForVisit() { return reasonForVisit; }
    public void setReasonForVisit(String reasonForVisit) { this.reasonForVisit = reasonForVisit; }
    public String getCurrentCondition() { return currentCondition; }
    public void setCurrentCondition(String currentCondition) { this.currentCondition = currentCondition; }
    public UUID getModalityId() { return modalityId; }
    public void setModalityId(UUID modalityId) { this.modalityId = modalityId; }
    public UUID getStatusId() { return statusId; }
    public void setStatusId(UUID statusId) { this.statusId = statusId; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
}
