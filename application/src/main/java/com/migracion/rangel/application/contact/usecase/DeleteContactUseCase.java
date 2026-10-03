package com.migracion.rangel.application.contact.usecase;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.application.contact.dto.ContactResponse;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.contact.event.ContactDeletedEvent;
public class DeleteContactUseCase {
    private final ContactRepository repository;

    public DeleteContactUseCase(ContactRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public ContactDeletedEvent execute(ContactId id) {
        var contact = repository.findById(id).orElseThrow(() -> new ContactNotFoundApplicationException(id));
        repository.delete(contact);
        return new ContactDeletedEvent(id, LocalDateTime.now(ZoneOffset.UTC));
    }
}
