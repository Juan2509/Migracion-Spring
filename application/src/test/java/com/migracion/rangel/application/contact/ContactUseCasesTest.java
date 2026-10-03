package com.migracion.rangel.application.contact;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.contact.command.*;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.application.contact.usecase.*;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;

class ContactUseCasesTest {
    @Test
    void crudPreservesCreatorAndSupportsUpdaterAndNull() {
        var fixture = new Fixture();
        var response = fixture.register.execute(new RegisterContactCommand("Ana", "a@example.com", "Notas", fixture.city.id(), fixture.creator.id()));
        var id = new ContactId(response.id());
        var get = new GetContactByIdUseCase(fixture.contacts);
        var list = new ListContactUseCase(fixture.contacts);
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        assertNull(response.updatedBy());
        var changed = fixture.update.execute(new UpdateContactCommand(id, "María", "m@example.com", "Nuevas", fixture.city.id(), fixture.updater.id()));
        assertEquals("María", changed.fullName());
        assertEquals("m@example.com", changed.email());
        assertEquals("Nuevas", changed.notes());
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals(response.createdBy(), changed.createdBy());
        assertEquals(fixture.updater.id().value(), changed.updatedBy());
        var noUpdater = fixture.update.execute(new UpdateContactCommand(id, changed.fullName(), changed.email(), changed.notes(), fixture.city.id(), null));
        assertNull(noUpdater.updatedBy());
        assertEquals(response.createdBy(), noUpdater.createdBy());
        var delete = new DeleteContactUseCase(fixture.contacts);
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(ContactNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(ContactNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(ContactNotFoundApplicationException.class, () -> fixture.update.execute(
                new UpdateContactCommand(id, "Ana", "a", "Notas", fixture.city.id(), null)));
    }
    @Test
    void missingReferencesDoNotSaveOrChangeContact() {
        var fixture = new Fixture();
        var missingCity = CityMunicipalityId.generate();
        var missingProfessional = ProfessionalId.generate();
        assertThrows(CityMunicipalityNotFoundApplicationException.class, () -> fixture.register.execute(
                new RegisterContactCommand("Ana", "a", "Notas", missingCity, fixture.creator.id())));
        assertThrows(ProfessionalNotFoundApplicationException.class, () -> fixture.register.execute(
                new RegisterContactCommand("Ana", "a", "Notas", fixture.city.id(), missingProfessional)));
        assertTrue(fixture.contacts.findAll().isEmpty());
        var original = fixture.register.execute(new RegisterContactCommand("Ana", "a", "Notas", fixture.city.id(), fixture.creator.id()));
        var id = new ContactId(original.id());
        assertThrows(CityMunicipalityNotFoundApplicationException.class, () -> fixture.update.execute(
                new UpdateContactCommand(id, "Otra", "b", "Nuevas", missingCity, null)));
        assertThrows(ProfessionalNotFoundApplicationException.class, () -> fixture.update.execute(
                new UpdateContactCommand(id, "Otra", "b", "Nuevas", fixture.city.id(), missingProfessional)));
        assertEquals(original, new GetContactByIdUseCase(fixture.contacts).execute(id));
        assertEquals(1, fixture.contacts.saves);
    }
    @Test
    void repeatedEmailsAndChangingCityAreAllowed() {
        var fixture = new Fixture();
        var first = fixture.register.execute(new RegisterContactCommand("Ana", "same@example.com", "Notas", fixture.city.id(), fixture.creator.id()));
        var second = fixture.register.execute(new RegisterContactCommand("María", "same@example.com", "Notas", fixture.city.id(), fixture.creator.id()));
        assertNotEquals(first.id(), second.id());
        var otherCity = fixture.cities.save(CityMunicipality.register("Bogotá", "BOG", "Ciudad", true, StateRegionId.generate()));
        var changed = fixture.update.execute(new UpdateContactCommand(new ContactId(first.id()), "Ana", "same@example.com", "Notas", otherCity.id(), null));
        assertEquals(otherCity.id().value(), changed.cityId());
        assertEquals(2, fixture.contacts.findAll().size());
    }
    private static class Fixture {
        final Contacts contacts = new Contacts();
        final Cities cities = new Cities();
        final Professionals professionals = new Professionals();
        final CityMunicipality city = cities.save(CityMunicipality.register("Medellín", "MED", "Ciudad", true, StateRegionId.generate()));
        final Professional creator = professionals.save(Professional.register(DocumentTypeId.generate(), "DOC1", "Ana", "Pérez", ProfessionalTypeId.generate(), "LIC1", true, city.id()));
        final Professional updater = professionals.save(Professional.register(DocumentTypeId.generate(), "DOC2", "Luis", "López", ProfessionalTypeId.generate(), "LIC2", true, city.id()));
        final RegisterContactUseCase register = new RegisterContactUseCase(contacts, cities, professionals);
        final UpdateContactUseCase update = new UpdateContactUseCase(contacts, cities, professionals);
    }
    private static class Contacts implements ContactRepository {
        private final Map<ContactId, Contact> values = new LinkedHashMap<>();
        private int saves;
        public Contact save(Contact contact) { saves++; values.put(contact.id(), contact); return contact; }
        public Optional<Contact> findById(ContactId id) { return Optional.ofNullable(values.get(id)); }
        public List<Contact> findAll() { return List.copyOf(values.values()); }
        public void delete(Contact contact) { values.remove(contact.id()); }
    }
    private static class Cities implements CityMunicipalityRepository {
        private final Map<CityMunicipalityId, CityMunicipality> values = new HashMap<>();
        public CityMunicipality save(CityMunicipality city) { values.put(city.id(), city); return city; }
        public Optional<CityMunicipality> findById(CityMunicipalityId id) { return Optional.ofNullable(values.get(id)); }
        public List<CityMunicipality> findAll() { return List.copyOf(values.values()); }
        public void delete(CityMunicipality city) { values.remove(city.id()); }
    }
    private static class Professionals implements ProfessionalRepository {
        private final Map<ProfessionalId, Professional> values = new HashMap<>();
        public Professional save(Professional professional) { values.put(professional.id(), professional); return professional; }
        public Optional<Professional> findById(ProfessionalId id) { return Optional.ofNullable(values.get(id)); }
        public List<Professional> findAll() { return List.copyOf(values.values()); }
        public void delete(Professional professional) { values.remove(professional.id()); }
        public boolean existsByDocumentNumber(String value) { throw new UnsupportedOperationException(); }
        public boolean existsByDocumentNumberAndIdNot(String value, ProfessionalId id) { throw new UnsupportedOperationException(); }
        public boolean existsByFirstName(String value) { throw new UnsupportedOperationException(); }
        public boolean existsByFirstNameAndIdNot(String value, ProfessionalId id) { throw new UnsupportedOperationException(); }
        public boolean existsByLastName(String value) { throw new UnsupportedOperationException(); }
        public boolean existsByLastNameAndIdNot(String value, ProfessionalId id) { throw new UnsupportedOperationException(); }
        public boolean existsByLicenseNumber(String value) { throw new UnsupportedOperationException(); }
        public boolean existsByLicenseNumberAndIdNot(String value, ProfessionalId id) { throw new UnsupportedOperationException(); }
    }
}
