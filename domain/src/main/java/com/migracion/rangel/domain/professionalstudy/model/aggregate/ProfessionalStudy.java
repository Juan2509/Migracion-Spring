package com.migracion.rangel.domain.professionalstudy.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.migracion.rangel.domain.professionalstudy.event.ProfessionalStudyRegisteredEvent;
import com.migracion.rangel.domain.professionalstudy.event.ProfessionalStudyUpdatedEvent;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;

public final class ProfessionalStudy extends AggregateRoot {
    private final ProfessionalStudyId id;
    private StudyId studyId;
    private ProfessionalId professionalId;
    private String title;
    private String university;
    private Boolean isValid;
    private String resolutionNumber;
    private CountryId countryId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ProfessionalStudy(ProfessionalStudyId id, StudyId studyId, ProfessionalId professionalId, String title, String university, Boolean isValid, String resolutionNumber, CountryId countryId,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(studyId, professionalId, title, university, isValid, resolutionNumber, countryId);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static ProfessionalStudy register(StudyId studyId, ProfessionalId professionalId, String title, String university, Boolean isValid, String resolutionNumber, CountryId countryId) {
        var now = LocalDateTime.now();
        var aggregate = new ProfessionalStudy(ProfessionalStudyId.generate(), studyId, professionalId, title, university, isValid, resolutionNumber, countryId, now, now);
        aggregate.recordEvent(new ProfessionalStudyRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ProfessionalStudy restore(ProfessionalStudyId id, StudyId studyId, ProfessionalId professionalId, String title, String university, Boolean isValid, String resolutionNumber, CountryId countryId,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ProfessionalStudy(id, studyId, professionalId, title, university, isValid, resolutionNumber, countryId, createdAt, updatedAt);
    }
    public void update(StudyId studyId, ProfessionalId professionalId, String title, String university, Boolean isValid, String resolutionNumber, CountryId countryId) {
        setDetails(studyId, professionalId, title, university, isValid, resolutionNumber, countryId);
        updatedAt = LocalDateTime.now();
        recordEvent(new ProfessionalStudyUpdatedEvent(id, updatedAt));
    }
    private void setDetails(StudyId studyId, ProfessionalId professionalId, String title, String university, Boolean isValid, String resolutionNumber, CountryId countryId) {
        // Validar todos los valores antes de cambiar el estado.
        Objects.requireNonNull(studyId, "studyId es obligatorio");
        Objects.requireNonNull(professionalId, "professionalId es obligatorio");
        validateText(title, 100, "title");
        validateText(university, 100, "university");
        Objects.requireNonNull(isValid, "isValid es obligatorio");
        if (resolutionNumber != null) { validateText(resolutionNumber, 60, "resolutionNumber"); }
        Objects.requireNonNull(countryId, "countryId es obligatorio");
        this.studyId = studyId;
        this.professionalId = professionalId;
        this.title = title;
        this.university = university;
        this.isValid = isValid;
        this.resolutionNumber = resolutionNumber;
        this.countryId = countryId;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public ProfessionalStudyId id() { return id; }
    public StudyId studyId() { return studyId; }
    public ProfessionalId professionalId() { return professionalId; }
    public String title() { return title; }
    public String university() { return university; }
    public Boolean isValid() { return isValid; }
    public String resolutionNumber() { return resolutionNumber; }
    public CountryId countryId() { return countryId; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
