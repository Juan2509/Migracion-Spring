package com.migracion.rangel.application.clinicalrecordstatus.command;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
public record UpdateClinicalRecordStatusCommand(ClinicalRecordStatusId id, String code, String name) {}

