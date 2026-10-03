package com.migracion.rangel.application.emailcontact.usecase;
import com.migracion.rangel.domain.emailcontact.port.repository.EmailContactRepository;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
import com.migracion.rangel.application.emailcontact.dto.EmailContactResponse;
import com.migracion.rangel.application.emailcontact.exception.EmailContactNotFoundApplicationException;
import com.migracion.rangel.application.emailcontact.exception.DuplicateEmailContactApplicationException;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.application.emailcontact.command.UpdateEmailContactCommand;
public class UpdateEmailContactUseCase {
    private final EmailContactRepository repository;
    private final ContactRepository contacts;
    public UpdateEmailContactUseCase(EmailContactRepository repository, ContactRepository contacts) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.contacts = java.util.Objects.requireNonNull(contacts);
    }
    public EmailContactResponse execute(UpdateEmailContactCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new EmailContactNotFoundApplicationException(id));
        contacts.findById(command.contactId()).orElseThrow(() -> new ContactNotFoundApplicationException(command.contactId()));
        if (repository.existsByEmailAndIdNot(command.email(), id)) { throw new DuplicateEmailContactApplicationException(); }
        aggregate.update(command.contactId(), command.email(), command.notes());
        return EmailContactResponse.from(repository.save(aggregate));
    }
}
