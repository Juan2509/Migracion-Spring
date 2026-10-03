package com.migracion.rangel.application.professionalstudy.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.professionalstudy.model.aggregate.ProfessionalStudy;
public record ProfessionalStudyResponse(UUID id, UUID studyId, UUID professionalId, String title, String university, Boolean isValid, String resolutionNumber, UUID countryId,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static ProfessionalStudyResponse from(ProfessionalStudy aggregate) {
        return new ProfessionalStudyResponse(aggregate.id().value(),
                aggregate.studyId().value(), aggregate.professionalId().value(), aggregate.title(), aggregate.university(), aggregate.isValid(), aggregate.resolutionNumber(), aggregate.countryId().value(),
                aggregate.createdAt(), aggregate.updatedAt());
    }
}
