package com.migracion.rangel.application.clinicalrecord.command;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
public record UpdateClinicalRecordCommand(ClinicalRecordId id, PatientId patientId, LocalDateTime creationDate, String recordNumber, OffsetDateTime openedAt, OffsetDateTime closedAt, ClinicalRecordStatusId statusId) {}

