package com.migracion.rangel.application.clinicalnote.dto;
import java.util.UUID;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.clinicalnote.model.aggregate.ClinicalNote;
public record ClinicalNoteResponse(UUID id, UUID encounterId, String subjective, String objective, String assessment, String plan, String additionalNotes, OffsetDateTime signedAt, UUID professionalId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
    public static ClinicalNoteResponse from(ClinicalNote aggregate) {
        return new ClinicalNoteResponse(aggregate.id().value(), aggregate.encounterId().value(), aggregate.subjective(), aggregate.objective(), aggregate.assessment(), aggregate.plan(), aggregate.additionalNotes(), aggregate.signedAt(), aggregate.professionalId().value(), aggregate.createdAt(), aggregate.updatedAt());
    }
}

