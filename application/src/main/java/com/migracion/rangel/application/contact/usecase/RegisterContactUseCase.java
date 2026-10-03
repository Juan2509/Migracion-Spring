package com.migracion.rangel.application.contact.usecase;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.application.contact.dto.ContactResponse;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.application.contact.command.RegisterContactCommand;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
public class RegisterContactUseCase {
    private final ContactRepository repository;
    private final CityMunicipalityRepository cities;
    private final ProfessionalRepository professionals;
    public RegisterContactUseCase(ContactRepository repository, CityMunicipalityRepository cities, ProfessionalRepository professionals) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.cities = java.util.Objects.requireNonNull(cities);
        this.professionals = java.util.Objects.requireNonNull(professionals);
    }
    public ContactResponse execute(RegisterContactCommand command) {
        var contact = Contact.register(command.fullName(), command.email(), command.notes(), command.cityId(), command.createdBy());
        cities.findById(command.cityId()).orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(command.cityId()));
        professionals.findById(command.createdBy()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.createdBy()));
        return ContactResponse.from(repository.save(contact));
    }
}
