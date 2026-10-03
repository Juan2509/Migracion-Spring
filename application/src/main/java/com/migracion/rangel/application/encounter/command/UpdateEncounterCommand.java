package com.migracion.rangel.application.encounter.command;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
public record UpdateEncounterCommand(EncounterId id, ClinicalRecordId clinicalRecordId, ProfessionalId professionalId, EncounterTypeId encounterTypeId, OffsetDateTime startedAt, OffsetDateTime endedAt, String reasonForVisit, String currentCondition, EncounterModalityId modalityId, EncounterStatusId statusId, ProfessionalId updatedBy) {}

