package com.migracion.rangel.infrastructure.professionalstudy.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateProfessionalStudyRequest(
        @NotNull UUID studyId,
        @NotNull UUID professionalId,
        @NotNull @Size(max = 100) String title,
        @NotNull @Size(max = 100) String university,
        @NotNull Boolean isValid,
        @Size(max = 60) String resolutionNumber,
        @NotNull UUID countryId
) {}
