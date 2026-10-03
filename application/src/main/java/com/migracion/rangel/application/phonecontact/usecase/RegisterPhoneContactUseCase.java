package com.migracion.rangel.application.phonecontact.usecase;
import com.migracion.rangel.domain.phonecontact.port.repository.PhoneContactRepository;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
import com.migracion.rangel.application.phonecontact.dto.PhoneContactResponse;
import com.migracion.rangel.application.phonecontact.exception.PhoneContactNotFoundApplicationException;

import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.application.phonecontact.command.RegisterPhoneContactCommand;
import com.migracion.rangel.domain.phonecontact.model.aggregate.PhoneContact;
public class RegisterPhoneContactUseCase {
    private final PhoneContactRepository repository;
    private final ContactRepository contacts;
    public RegisterPhoneContactUseCase(PhoneContactRepository repository, ContactRepository contacts) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.contacts = java.util.Objects.requireNonNull(contacts);
    }
    public PhoneContactResponse execute(RegisterPhoneContactCommand command) {
        var aggregate = PhoneContact.register(command.contactId(), command.phone(), command.notes());
        contacts.findById(command.contactId()).orElseThrow(() -> new ContactNotFoundApplicationException(command.contactId()));

        return PhoneContactResponse.from(repository.save(aggregate));
    }
}
