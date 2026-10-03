package com.migracion.rangel.application.phonecontact;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.phonecontact.command.*;
import com.migracion.rangel.application.phonecontact.usecase.*;
import com.migracion.rangel.application.phonecontact.exception.PhoneContactNotFoundApplicationException;

import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.domain.phonecontact.model.aggregate.PhoneContact;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
import com.migracion.rangel.domain.phonecontact.port.repository.PhoneContactRepository;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

class PhoneContactUseCasesTest {
    @Test
    void crudCanChangeContactAndReportsMissingRecord() {
        var fixture = new Fixture();
        var original = fixture.register.execute(new RegisterPhoneContactCommand(fixture.first.id(), "+57 3001234567", "Notas"));
        var id = new PhoneContactId(original.id());
        var get = new GetPhoneContactByIdUseCase(fixture.repository);
        var list = new ListPhoneContactUseCase(fixture.repository);
        assertEquals(original, get.execute(id));
        assertEquals(List.of(original), list.execute());
        var changed = fixture.update.execute(new UpdatePhoneContactCommand(id, fixture.second.id(), "+57 3007654321", null));
        assertEquals(original.id(), changed.id());
        assertEquals(fixture.second.id().value(), changed.contactId());
        assertEquals("+57 3007654321", changed.phone());
        assertNull(changed.notes());

        var delete = new DeletePhoneContactUseCase(fixture.repository);
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(PhoneContactNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(PhoneContactNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(PhoneContactNotFoundApplicationException.class, () -> fixture.update.execute(
                new UpdatePhoneContactCommand(id, fixture.first.id(), "+57 3001234567", "Notas")));
    }
    @Test
    void missingContactCannotRegisterOrModifyRecord() {
        var fixture = new Fixture();
        var missing = ContactId.generate();
        assertThrows(ContactNotFoundApplicationException.class, () -> fixture.register.execute(
                new RegisterPhoneContactCommand(missing, "+57 3001234567", "Notas")));
        assertTrue(fixture.repository.findAll().isEmpty());
        var original = fixture.register.execute(new RegisterPhoneContactCommand(fixture.first.id(), "+57 3001234567", "Notas"));
        var id = new PhoneContactId(original.id());
        assertThrows(ContactNotFoundApplicationException.class, () -> fixture.update.execute(
                new UpdatePhoneContactCommand(id, missing, "+57 3007654321", "Nuevas")));
        assertEquals(original, new GetPhoneContactByIdUseCase(fixture.repository).execute(id));
        assertEquals(1, fixture.repository.saves);
    }
    @Test
    void repeatedPhonesAreAllowedForDifferentContacts() {
        var fixture = new Fixture();
        var first = fixture.register.execute(new RegisterPhoneContactCommand(fixture.first.id(), "+57 3001234567", "Notas"));
        var second = fixture.register.execute(new RegisterPhoneContactCommand(fixture.second.id(), "+57 3001234567", null));
        assertNotEquals(first.id(), second.id());
        var changed = fixture.update.execute(new UpdatePhoneContactCommand(new PhoneContactId(second.id()), fixture.first.id(), "+57 3001234567", null));
        assertEquals("+57 3001234567", changed.phone());
        assertEquals(2, fixture.repository.findAll().size());
    }
    private static class Fixture {
        final Contacts contacts = new Contacts();
        final Repository repository = new Repository();
        final Contact first = contacts.save(Contact.register("Ana", "a@example.com", "Notas", CityMunicipalityId.generate(), ProfessionalId.generate()));
        final Contact second = contacts.save(Contact.register("Luis", "l@example.com", "Notas", CityMunicipalityId.generate(), ProfessionalId.generate()));
        final RegisterPhoneContactUseCase register = new RegisterPhoneContactUseCase(repository, contacts);
        final UpdatePhoneContactUseCase update = new UpdatePhoneContactUseCase(repository, contacts);
    }
    private static class Repository implements PhoneContactRepository {
        private final Map<PhoneContactId, PhoneContact> values = new LinkedHashMap<>();
        private int saves;
        public PhoneContact save(PhoneContact aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<PhoneContact> findById(PhoneContactId id) { return Optional.ofNullable(values.get(id)); }
        public List<PhoneContact> findAll() { return List.copyOf(values.values()); }
        public void delete(PhoneContact aggregate) { values.remove(aggregate.id()); }

    }
    private static class Contacts implements ContactRepository {
        private final Map<ContactId, Contact> values = new HashMap<>();
        public Contact save(Contact contact) { values.put(contact.id(), contact); return contact; }
        public Optional<Contact> findById(ContactId id) { return Optional.ofNullable(values.get(id)); }
        public List<Contact> findAll() { return List.copyOf(values.values()); }
        public void delete(Contact contact) { values.remove(contact.id()); }
    }
}
