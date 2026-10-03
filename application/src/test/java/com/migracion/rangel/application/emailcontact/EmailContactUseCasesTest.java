package com.migracion.rangel.application.emailcontact;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.emailcontact.command.*;
import com.migracion.rangel.application.emailcontact.usecase.*;
import com.migracion.rangel.application.emailcontact.exception.EmailContactNotFoundApplicationException;
import com.migracion.rangel.application.emailcontact.exception.DuplicateEmailContactApplicationException;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.domain.emailcontact.model.aggregate.EmailContact;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
import com.migracion.rangel.domain.emailcontact.port.repository.EmailContactRepository;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

class EmailContactUseCasesTest {
    @Test
    void crudCanChangeContactAndReportsMissingRecord() {
        var fixture = new Fixture();
        var original = fixture.register.execute(new RegisterEmailContactCommand(fixture.first.id(), "ana@example.com", "Notas"));
        var id = new EmailContactId(original.id());
        var get = new GetEmailContactByIdUseCase(fixture.repository);
        var list = new ListEmailContactUseCase(fixture.repository);
        assertEquals(original, get.execute(id));
        assertEquals(List.of(original), list.execute());
        var changed = fixture.update.execute(new UpdateEmailContactCommand(id, fixture.second.id(), "otro@example.com", "Nuevas"));
        assertEquals(original.id(), changed.id());
        assertEquals(fixture.second.id().value(), changed.contactId());
        assertEquals("otro@example.com", changed.email());
        assertEquals("Nuevas", changed.notes());
        assertEquals(original.createdAt(), changed.createdAt());
        var delete = new DeleteEmailContactUseCase(fixture.repository);
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(EmailContactNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(EmailContactNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(EmailContactNotFoundApplicationException.class, () -> fixture.update.execute(
                new UpdateEmailContactCommand(id, fixture.first.id(), "ana@example.com", "Notas")));
    }
    @Test
    void missingContactCannotRegisterOrModifyRecord() {
        var fixture = new Fixture();
        var missing = ContactId.generate();
        assertThrows(ContactNotFoundApplicationException.class, () -> fixture.register.execute(
                new RegisterEmailContactCommand(missing, "ana@example.com", "Notas")));
        assertTrue(fixture.repository.findAll().isEmpty());
        var original = fixture.register.execute(new RegisterEmailContactCommand(fixture.first.id(), "ana@example.com", "Notas"));
        var id = new EmailContactId(original.id());
        assertThrows(ContactNotFoundApplicationException.class, () -> fixture.update.execute(
                new UpdateEmailContactCommand(id, missing, "otro@example.com", "Nuevas")));
        assertEquals(original, new GetEmailContactByIdUseCase(fixture.repository).execute(id));
        assertEquals(1, fixture.repository.saves);
    }
    @Test
    void uniquenessIsGlobalButAllowsOwnValue() {
        var fixture = new Fixture();
        var first = fixture.register.execute(new RegisterEmailContactCommand(fixture.first.id(), "ana@example.com", "Notas"));
        var second = fixture.register.execute(new RegisterEmailContactCommand(fixture.second.id(), "otro@example.com", "Notas"));
        assertThrows(DuplicateEmailContactApplicationException.class, () -> fixture.register.execute(
                new RegisterEmailContactCommand(fixture.second.id(), "ana@example.com", "Notas")));
        var secondId = new EmailContactId(second.id());
        assertThrows(DuplicateEmailContactApplicationException.class, () -> fixture.update.execute(
                new UpdateEmailContactCommand(secondId, fixture.second.id(), "ana@example.com", "Nuevas")));
        assertEquals(second, new GetEmailContactByIdUseCase(fixture.repository).execute(secondId));
        var own = fixture.update.execute(new UpdateEmailContactCommand(new EmailContactId(first.id()), fixture.first.id(), "ana@example.com", "Nuevas"));
        assertEquals("ana@example.com", own.email());
        assertEquals(3, fixture.repository.saves);
    }
    private static class Fixture {
        final Contacts contacts = new Contacts();
        final Repository repository = new Repository();
        final Contact first = contacts.save(Contact.register("Ana", "a@example.com", "Notas", CityMunicipalityId.generate(), ProfessionalId.generate()));
        final Contact second = contacts.save(Contact.register("Luis", "l@example.com", "Notas", CityMunicipalityId.generate(), ProfessionalId.generate()));
        final RegisterEmailContactUseCase register = new RegisterEmailContactUseCase(repository, contacts);
        final UpdateEmailContactUseCase update = new UpdateEmailContactUseCase(repository, contacts);
    }
    private static class Repository implements EmailContactRepository {
        private final Map<EmailContactId, EmailContact> values = new LinkedHashMap<>();
        private int saves;
        public EmailContact save(EmailContact aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<EmailContact> findById(EmailContactId id) { return Optional.ofNullable(values.get(id)); }
        public List<EmailContact> findAll() { return List.copyOf(values.values()); }
        public void delete(EmailContact aggregate) { values.remove(aggregate.id()); }
        public boolean existsByEmail(String value) { return values.values().stream().anyMatch(a -> a.email().equals(value)); }
        public boolean existsByEmailAndIdNot(String value, EmailContactId id) {
            return values.values().stream().anyMatch(a -> !a.id().equals(id) && a.email().equals(value));
        }
    }
    private static class Contacts implements ContactRepository {
        private final Map<ContactId, Contact> values = new HashMap<>();
        public Contact save(Contact contact) { values.put(contact.id(), contact); return contact; }
        public Optional<Contact> findById(ContactId id) { return Optional.ofNullable(values.get(id)); }
        public List<Contact> findAll() { return List.copyOf(values.values()); }
        public void delete(Contact contact) { values.remove(contact.id()); }
    }
}
