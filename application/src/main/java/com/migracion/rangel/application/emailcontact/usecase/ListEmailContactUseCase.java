package com.migracion.rangel.application.emailcontact.usecase;
import com.migracion.rangel.domain.emailcontact.port.repository.EmailContactRepository;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
import com.migracion.rangel.application.emailcontact.dto.EmailContactResponse;
import com.migracion.rangel.application.emailcontact.exception.EmailContactNotFoundApplicationException;


import java.util.List;
public class ListEmailContactUseCase {
    private final EmailContactRepository repository;

    public ListEmailContactUseCase(EmailContactRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public List<EmailContactResponse> execute() { return repository.findAll().stream().map(EmailContactResponse::from).toList(); }
}
