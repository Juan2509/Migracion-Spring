package com.migracion.rangel.application.contact.usecase;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.application.contact.dto.ContactResponse;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;


public class GetContactByIdUseCase {
    private final ContactRepository repository;

    public GetContactByIdUseCase(ContactRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public ContactResponse execute(ContactId id) { return ContactResponse.from(repository.findById(id).orElseThrow(() -> new ContactNotFoundApplicationException(id))); }
}
