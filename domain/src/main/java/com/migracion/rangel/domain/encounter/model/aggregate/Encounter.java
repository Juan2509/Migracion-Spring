package com.migracion.rangel.domain.encounter.model.aggregate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;

import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.encounter.event.EncounterRegisteredEvent;
import com.migracion.rangel.domain.encounter.event.EncounterUpdatedEvent;
public final class Encounter extends AggregateRoot {
    private final EncounterId id;
    private ClinicalRecordId clinicalRecordId;
    private ProfessionalId professionalId;
    private EncounterTypeId encounterTypeId;
    private OffsetDateTime startedAt;
    private OffsetDateTime endedAt;
    private String reasonForVisit;
    private String currentCondition;
    private EncounterModalityId modalityId;
    private EncounterStatusId statusId;
    private final OffsetDateTime createdAt;
    private final ProfessionalId createdBy;
    private OffsetDateTime updatedAt;
    private ProfessionalId updatedBy;
    private Encounter(EncounterId id, ClinicalRecordId clinicalRecordId, ProfessionalId professionalId, EncounterTypeId encounterTypeId, OffsetDateTime startedAt, OffsetDateTime endedAt, String reasonForVisit, String currentCondition, EncounterModalityId modalityId, EncounterStatusId statusId, OffsetDateTime createdAt,
            ProfessionalId createdBy, OffsetDateTime updatedAt, ProfessionalId updatedBy) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.createdBy = Objects.requireNonNull(createdBy, "createdBy es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
        setDetails(clinicalRecordId, professionalId, encounterTypeId, startedAt, endedAt, reasonForVisit, currentCondition, modalityId, statusId, updatedBy);
    }
    public static Encounter register(ClinicalRecordId clinicalRecordId, ProfessionalId professionalId, EncounterTypeId encounterTypeId, OffsetDateTime startedAt, OffsetDateTime endedAt, String reasonForVisit, String currentCondition, EncounterModalityId modalityId, EncounterStatusId statusId, ProfessionalId createdBy, ProfessionalId updatedBy) {
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        var aggregate = new Encounter(EncounterId.generate(), clinicalRecordId, professionalId, encounterTypeId, startedAt, endedAt, reasonForVisit, currentCondition, modalityId, statusId, now, createdBy, now, updatedBy);
        aggregate.recordEvent(new EncounterRegisteredEvent(aggregate.id, now.toLocalDateTime()));
        return aggregate;
    }
    public static Encounter restore(EncounterId id, ClinicalRecordId clinicalRecordId, ProfessionalId professionalId, EncounterTypeId encounterTypeId, OffsetDateTime startedAt, OffsetDateTime endedAt, String reasonForVisit, String currentCondition, EncounterModalityId modalityId, EncounterStatusId statusId, OffsetDateTime createdAt,
            ProfessionalId createdBy, OffsetDateTime updatedAt, ProfessionalId updatedBy) {
        return new Encounter(id, clinicalRecordId, professionalId, encounterTypeId, startedAt, endedAt, reasonForVisit, currentCondition, modalityId, statusId, createdAt, createdBy, updatedAt, updatedBy);
    }
    public void update(ClinicalRecordId clinicalRecordId, ProfessionalId professionalId, EncounterTypeId encounterTypeId, OffsetDateTime startedAt, OffsetDateTime endedAt, String reasonForVisit, String currentCondition, EncounterModalityId modalityId, EncounterStatusId statusId, ProfessionalId updatedBy) {
        setDetails(clinicalRecordId, professionalId, encounterTypeId, startedAt, endedAt, reasonForVisit, currentCondition, modalityId, statusId, updatedBy);
        updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
        recordEvent(new EncounterUpdatedEvent(id, updatedAt.toLocalDateTime()));
    }
    private void setDetails(ClinicalRecordId clinicalRecordId, ProfessionalId professionalId, EncounterTypeId encounterTypeId, OffsetDateTime startedAt, OffsetDateTime endedAt, String reasonForVisit, String currentCondition, EncounterModalityId modalityId, EncounterStatusId statusId, ProfessionalId updatedBy) {
        // Validar todos los campos antes de cambiar el agregado.
        Objects.requireNonNull(clinicalRecordId, "clinicalRecordId es obligatorio");
        Objects.requireNonNull(professionalId, "professionalId es obligatorio");
        Objects.requireNonNull(encounterTypeId, "encounterTypeId es obligatorio");
        Objects.requireNonNull(startedAt, "startedAt es obligatorio");
        Objects.requireNonNull(endedAt, "endedAt es obligatorio");
        Objects.requireNonNull(reasonForVisit, "reasonForVisit es obligatorio");
        Objects.requireNonNull(currentCondition, "currentCondition es obligatorio");
        Objects.requireNonNull(modalityId, "modalityId es obligatorio");
        Objects.requireNonNull(statusId, "statusId es obligatorio");
        Objects.requireNonNull(updatedBy, "updatedBy es obligatorio");
        this.clinicalRecordId = clinicalRecordId;
        this.professionalId = professionalId;
        this.encounterTypeId = encounterTypeId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.reasonForVisit = reasonForVisit;
        this.currentCondition = currentCondition;
        this.modalityId = modalityId;
        this.statusId = statusId;
        this.updatedBy = updatedBy;
    }
    public EncounterId id() { return id; }
    public ClinicalRecordId clinicalRecordId() { return clinicalRecordId; }
    public ProfessionalId professionalId() { return professionalId; }
    public EncounterTypeId encounterTypeId() { return encounterTypeId; }
    public OffsetDateTime startedAt() { return startedAt; }
    public OffsetDateTime endedAt() { return endedAt; }
    public String reasonForVisit() { return reasonForVisit; }
    public String currentCondition() { return currentCondition; }
    public EncounterModalityId modalityId() { return modalityId; }
    public EncounterStatusId statusId() { return statusId; }
    public OffsetDateTime createdAt() { return createdAt; }
    public ProfessionalId createdBy() { return createdBy; }
    public OffsetDateTime updatedAt() { return updatedAt; }
    public ProfessionalId updatedBy() { return updatedBy; }
}
