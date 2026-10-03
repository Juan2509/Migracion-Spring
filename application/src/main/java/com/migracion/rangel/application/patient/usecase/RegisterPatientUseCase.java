package com.migracion.rangel.application.patient.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.patient.model.aggregate.Patient;
import com.migracion.rangel.application.patient.command.RegisterPatientCommand;
import com.migracion.rangel.application.patient.dto.PatientResponse;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.application.patient.exception.DuplicatePatientApplicationException;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;
import com.migracion.rangel.application.gender.exception.GenderNotFoundApplicationException;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
public class RegisterPatientUseCase {
    private final PatientRepository repository;
    private final DocumentTypeRepository documents;
    private final GenderRepository genders;
    private final CityMunicipalityRepository cities;
    private final ProfessionalRepository professionals;
    public RegisterPatientUseCase(PatientRepository repository, DocumentTypeRepository documents, GenderRepository genders, CityMunicipalityRepository cities, ProfessionalRepository professionals) {
        this.repository = Objects.requireNonNull(repository);
        this.documents = Objects.requireNonNull(documents);
        this.genders = Objects.requireNonNull(genders);
        this.cities = Objects.requireNonNull(cities);
        this.professionals = Objects.requireNonNull(professionals);
    }
    public PatientResponse execute(RegisterPatientCommand command) {
        var patient = Patient.register(command.documentTypeId(), command.documentNumber(), command.firstName(), command.middleName(), command.lastName(), command.secondLastName(), command.birthDate(), command.biologicalSexId(), command.genderIdentity(), command.email(), command.phone(), command.address(), command.active(), command.cityId(), command.createdBy());
        documents.findById(command.documentTypeId()).orElseThrow(() -> new DocumentTypeNotFoundApplicationException(command.documentTypeId()));
        genders.findById(command.biologicalSexId()).orElseThrow(() -> new GenderNotFoundApplicationException(command.biologicalSexId()));
        genders.findById(command.genderIdentity()).orElseThrow(() -> new GenderNotFoundApplicationException(command.genderIdentity()));
        cities.findById(command.cityId()).orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(command.cityId()));
        if (command.createdBy() != null) {
            professionals.findById(command.createdBy()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.createdBy()));
        }
        if (repository.existsByEmail(command.email())) {
            throw new DuplicatePatientApplicationException();
        }
        return PatientResponse.from(repository.save(patient));
    }
}
