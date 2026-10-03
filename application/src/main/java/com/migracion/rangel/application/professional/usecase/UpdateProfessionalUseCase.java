package com.migracion.rangel.application.professional.usecase;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.application.professional.dto.ProfessionalResponse;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.DuplicateProfessionalApplicationException;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import com.migracion.rangel.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.migracion.rangel.application.professional.command.UpdateProfessionalCommand;
public class UpdateProfessionalUseCase {
    private final ProfessionalRepository repository;
    private final DocumentTypeRepository documents;
    private final ProfessionalTypeRepository types;
    private final CityMunicipalityRepository cities;
    public UpdateProfessionalUseCase(ProfessionalRepository repository, DocumentTypeRepository documents, ProfessionalTypeRepository types, CityMunicipalityRepository cities) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.documents = java.util.Objects.requireNonNull(documents);
        this.types = java.util.Objects.requireNonNull(types);
        this.cities = java.util.Objects.requireNonNull(cities);
    }
    public ProfessionalResponse execute(UpdateProfessionalCommand command) {
        var id = command.id();
        var professional = repository.findById(id).orElseThrow(() -> new ProfessionalNotFoundApplicationException(id));
        documents.findById(command.documentTypeId()).orElseThrow(() -> new DocumentTypeNotFoundApplicationException(command.documentTypeId()));
        types.findById(command.professionalType()).orElseThrow(() -> new ProfessionalTypeNotFoundApplicationException(command.professionalType()));
        cities.findById(command.cityId()).orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(command.cityId()));
        if (repository.existsByDocumentNumberAndIdNot(command.documentNumber(), id)) { throw new DuplicateProfessionalApplicationException("documentNumber"); }
        if (repository.existsByFirstNameAndIdNot(command.firstName(), id)) { throw new DuplicateProfessionalApplicationException("firstName"); }
        if (repository.existsByLastNameAndIdNot(command.lastName(), id)) { throw new DuplicateProfessionalApplicationException("lastName"); }
        if (repository.existsByLicenseNumberAndIdNot(command.licenseNumber(), id)) { throw new DuplicateProfessionalApplicationException("licenseNumber"); }
        professional.update(command.documentTypeId(), command.documentNumber(), command.firstName(), command.lastName(), command.professionalType(), command.licenseNumber(), command.active(), command.cityId());
        return ProfessionalResponse.from(repository.save(professional));
    }
}
