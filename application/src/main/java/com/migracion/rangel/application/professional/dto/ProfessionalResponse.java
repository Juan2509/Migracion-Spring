package com.migracion.rangel.application.professional.dto;
import java.time.OffsetDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
public record ProfessionalResponse(UUID id, UUID documentTypeId, String documentNumber, String firstName, String lastName, UUID professionalType, String licenseNumber, Boolean active, UUID cityId,
        OffsetDateTime createdAt, OffsetDateTime updatedAt) {
    public static ProfessionalResponse from(Professional professional) {
        return new ProfessionalResponse(professional.id().value(),
                professional.documentTypeId().value(), professional.documentNumber(), professional.firstName(), professional.lastName(), professional.professionalType().value(), professional.licenseNumber(), professional.active(), professional.cityId().value(),
                professional.createdAt(), professional.updatedAt());
    }
}
