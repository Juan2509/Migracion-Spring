package com.migracion.rangel.application.clinicalnote.command;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
public record RegisterClinicalNoteCommand(EncounterId encounterId, String subjective, String objective, String assessment, String plan, String additionalNotes, OffsetDateTime signedAt, ProfessionalId professionalId) {}


