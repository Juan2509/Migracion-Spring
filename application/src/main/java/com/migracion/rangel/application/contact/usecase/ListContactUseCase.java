package com.migracion.rangel.application.contact.usecase;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.application.contact.dto.ContactResponse;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;

import java.util.List;
public class ListContactUseCase {
    private final ContactRepository repository;

    public ListContactUseCase(ContactRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public List<ContactResponse> execute() { return repository.findAll().stream().map(ContactResponse::from).toList(); }
}
