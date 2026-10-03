package com.migracion.rangel.application.professionalstudy.command;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
public record UpdateProfessionalStudyCommand(ProfessionalStudyId id, StudyId studyId, ProfessionalId professionalId, String title, String university, Boolean isValid, String resolutionNumber, CountryId countryId) {}
