package com.migracion.rangel.application.professionalstudy.command;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;

public record RegisterProfessionalStudyCommand(StudyId studyId, ProfessionalId professionalId, String title, String university, Boolean isValid, String resolutionNumber, CountryId countryId) {}
