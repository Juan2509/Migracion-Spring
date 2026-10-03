package com.migracion.rangel.application.professionalstudy.usecase;
import com.migracion.rangel.domain.professionalstudy.port.repository.ProfessionalStudyRepository;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.migracion.rangel.application.professionalstudy.dto.ProfessionalStudyResponse;
import com.migracion.rangel.application.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;
import com.migracion.rangel.application.study.exception.StudyNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
import com.migracion.rangel.application.professionalstudy.command.UpdateProfessionalStudyCommand;
public class UpdateProfessionalStudyUseCase {
    private final ProfessionalStudyRepository repository;
    private final StudyRepository studies;
    private final ProfessionalRepository professionals;
    private final CountryRepository countries;
    public UpdateProfessionalStudyUseCase(ProfessionalStudyRepository repository, StudyRepository studies, ProfessionalRepository professionals, CountryRepository countries) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.studies = java.util.Objects.requireNonNull(studies);
        this.professionals = java.util.Objects.requireNonNull(professionals);
        this.countries = java.util.Objects.requireNonNull(countries);
    }
    public ProfessionalStudyResponse execute(UpdateProfessionalStudyCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ProfessionalStudyNotFoundApplicationException(id));
        studies.findById(command.studyId()).orElseThrow(() -> new StudyNotFoundApplicationException(command.studyId()));
        professionals.findById(command.professionalId()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.professionalId()));
        countries.findById(command.countryId()).orElseThrow(() -> new CountryNotFoundApplicationException(command.countryId()));
        aggregate.update(command.studyId(), command.professionalId(), command.title(), command.university(), command.isValid(), command.resolutionNumber(), command.countryId());
        return ProfessionalStudyResponse.from(repository.save(aggregate));
    }
}
