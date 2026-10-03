package com.migracion.rangel.application.patientallergy.command;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
public record RegisterPatientAllergyCommand(PatientId patientId, String substance, String reaction, String severity, Boolean active, OffsetDateTime recordedAt, ProfessionalId recordedBy) {}

