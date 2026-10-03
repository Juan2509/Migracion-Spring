package com.migracion.rangel.application.patientallergy.dto;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.patientallergy.model.aggregate.PatientAllergy;
public record PatientAllergyResponse(UUID id, UUID patientId, String substance, String reaction, String severity, Boolean active, OffsetDateTime recordedAt, UUID recordedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static PatientAllergyResponse from(PatientAllergy aggregate) {
        return new PatientAllergyResponse(aggregate.id().value(), aggregate.patientId().value(), aggregate.substance(), aggregate.reaction(), aggregate.severity(), aggregate.active(), aggregate.recordedAt(), aggregate.recordedBy().value(), aggregate.createdAt(), aggregate.updatedAt());
    }
}
