package com.migracion.rangel.application.contact.usecase;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.application.contact.dto.ContactResponse;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.application.contact.command.UpdateContactCommand;
public class UpdateContactUseCase {
    private final ContactRepository repository;
    private final CityMunicipalityRepository cities;
    private final ProfessionalRepository professionals;
    public UpdateContactUseCase(ContactRepository repository, CityMunicipalityRepository cities, ProfessionalRepository professionals) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.cities = java.util.Objects.requireNonNull(cities);
        this.professionals = java.util.Objects.requireNonNull(professionals);
    }
    public ContactResponse execute(UpdateContactCommand command) {
        var id = command.id();
        var contact = repository.findById(id).orElseThrow(() -> new ContactNotFoundApplicationException(id));
        cities.findById(command.cityId()).orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(command.cityId()));
        if (command.updatedBy() != null) {
            professionals.findById(command.updatedBy()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.updatedBy()));
        }
        contact.update(command.fullName(), command.email(), command.notes(), command.cityId(), command.updatedBy());
        return ContactResponse.from(repository.save(contact));
    }
}
