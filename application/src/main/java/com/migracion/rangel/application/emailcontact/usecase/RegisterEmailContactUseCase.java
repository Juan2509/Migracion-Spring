package com.migracion.rangel.application.emailcontact.usecase;
import com.migracion.rangel.domain.emailcontact.port.repository.EmailContactRepository;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
import com.migracion.rangel.application.emailcontact.dto.EmailContactResponse;
import com.migracion.rangel.application.emailcontact.exception.EmailContactNotFoundApplicationException;
import com.migracion.rangel.application.emailcontact.exception.DuplicateEmailContactApplicationException;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.application.emailcontact.command.RegisterEmailContactCommand;
import com.migracion.rangel.domain.emailcontact.model.aggregate.EmailContact;
public class RegisterEmailContactUseCase {
    private final EmailContactRepository repository;
    private final ContactRepository contacts;
    public RegisterEmailContactUseCase(EmailContactRepository repository, ContactRepository contacts) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.contacts = java.util.Objects.requireNonNull(contacts);
    }
    public EmailContactResponse execute(RegisterEmailContactCommand command) {
        var aggregate = EmailContact.register(command.contactId(), command.email(), command.notes());
        contacts.findById(command.contactId()).orElseThrow(() -> new ContactNotFoundApplicationException(command.contactId()));
        if (repository.existsByEmail(command.email())) { throw new DuplicateEmailContactApplicationException(); }
        return EmailContactResponse.from(repository.save(aggregate));
    }
}
