package com.migracion.rangel.domain.clinicalrecord.model.aggregate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.clinicalrecord.event.ClinicalRecordRegisteredEvent;
import com.migracion.rangel.domain.clinicalrecord.event.ClinicalRecordUpdatedEvent;
public final class ClinicalRecord extends AggregateRoot {
    private final ClinicalRecordId id;
    private PatientId patientId;
    private LocalDateTime creationDate;
    private String recordNumber;
    private OffsetDateTime openedAt;
    private OffsetDateTime closedAt;
    private ClinicalRecordStatusId statusId;
    private final OffsetDateTime createdAt;

    private final ProfessionalId createdBy;
    private ClinicalRecord(ClinicalRecordId id, PatientId patientId, LocalDateTime creationDate, String recordNumber, OffsetDateTime openedAt, OffsetDateTime closedAt, ClinicalRecordStatusId statusId, ProfessionalId createdBy, OffsetDateTime createdAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(patientId, creationDate, recordNumber, openedAt, closedAt, statusId);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");

        this.createdBy = Objects.requireNonNull(createdBy, "createdBy es obligatorio");
    }
    public static ClinicalRecord register(PatientId patientId, LocalDateTime creationDate, String recordNumber, OffsetDateTime openedAt, OffsetDateTime closedAt, ClinicalRecordStatusId statusId, ProfessionalId createdBy) {
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        var aggregate = new ClinicalRecord(ClinicalRecordId.generate(), patientId, creationDate, recordNumber, openedAt, closedAt, statusId, createdBy, now);
        aggregate.recordEvent(new ClinicalRecordRegisteredEvent(aggregate.id, now.toLocalDateTime()));
        return aggregate;
    }
    public static ClinicalRecord restore(ClinicalRecordId id, PatientId patientId, LocalDateTime creationDate, String recordNumber, OffsetDateTime openedAt, OffsetDateTime closedAt, ClinicalRecordStatusId statusId, ProfessionalId createdBy, OffsetDateTime createdAt) {
        return new ClinicalRecord(id, patientId, creationDate, recordNumber, openedAt, closedAt, statusId, createdBy, createdAt);
    }
    public void update(PatientId patientId, LocalDateTime creationDate, String recordNumber, OffsetDateTime openedAt, OffsetDateTime closedAt, ClinicalRecordStatusId statusId) {
        setDetails(patientId, creationDate, recordNumber, openedAt, closedAt, statusId);
        var now = OffsetDateTime.now(ZoneOffset.UTC);

        recordEvent(new ClinicalRecordUpdatedEvent(id, now.toLocalDateTime()));
    }
    private void setDetails(PatientId patientId, LocalDateTime creationDate, String recordNumber, OffsetDateTime openedAt, OffsetDateTime closedAt, ClinicalRecordStatusId statusId) {
        // Validar todo antes de modificar el estado.
        Objects.requireNonNull(patientId, "patientId es obligatorio");
        Objects.requireNonNull(creationDate, "creationDate es obligatorio");
        validateText(recordNumber, 50, "recordNumber");
        Objects.requireNonNull(openedAt, "openedAt es obligatorio");
        Objects.requireNonNull(closedAt, "closedAt es obligatorio");
        Objects.requireNonNull(statusId, "statusId es obligatorio");
        this.patientId = patientId;
        this.creationDate = creationDate;
        this.recordNumber = recordNumber;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.statusId = statusId;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public ClinicalRecordId id() { return id; }
    public PatientId patientId() { return patientId; }
    public LocalDateTime creationDate() { return creationDate; }
    public String recordNumber() { return recordNumber; }
    public OffsetDateTime openedAt() { return openedAt; }
    public OffsetDateTime closedAt() { return closedAt; }
    public ClinicalRecordStatusId statusId() { return statusId; }
    public ProfessionalId createdBy() { return createdBy; }
    public OffsetDateTime createdAt() { return createdAt; }

}
