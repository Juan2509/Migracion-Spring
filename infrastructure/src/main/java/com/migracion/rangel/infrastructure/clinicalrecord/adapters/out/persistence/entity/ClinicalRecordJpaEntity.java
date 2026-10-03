package com.migracion.rangel.infrastructure.clinicalrecord.adapters.out.persistence.entity;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import jakarta.persistence.*;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;
@Entity
@Table(name = "clinical_records")
public class ClinicalRecordJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "record_number", nullable = false, length = 50)
    private String recordNumber;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "opened_at", nullable = false)
    private OffsetDateTime openedAt;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "closed_at", nullable = false)
    private OffsetDateTime closedAt;

    @Column(name = "status_id", nullable = false)
    private UUID statusId;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    public ClinicalRecordJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPatientId() { return patientId; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public String getRecordNumber() { return recordNumber; }
    public void setRecordNumber(String recordNumber) { this.recordNumber = recordNumber; }
    public OffsetDateTime getOpenedAt() { return openedAt; }
    public void setOpenedAt(OffsetDateTime openedAt) { this.openedAt = openedAt; }
    public OffsetDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(OffsetDateTime closedAt) { this.closedAt = closedAt; }
    public UUID getStatusId() { return statusId; }
    public void setStatusId(UUID statusId) { this.statusId = statusId; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
