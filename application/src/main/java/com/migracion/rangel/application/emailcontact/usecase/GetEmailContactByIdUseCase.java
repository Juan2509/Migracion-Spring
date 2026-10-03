package com.migracion.rangel.application.emailcontact.usecase;
import com.migracion.rangel.domain.emailcontact.port.repository.EmailContactRepository;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
import com.migracion.rangel.application.emailcontact.dto.EmailContactResponse;
import com.migracion.rangel.application.emailcontact.exception.EmailContactNotFoundApplicationException;



public class GetEmailContactByIdUseCase {
    private final EmailContactRepository repository;

    public GetEmailContactByIdUseCase(EmailContactRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public EmailContactResponse execute(EmailContactId id) { return EmailContactResponse.from(repository.findById(id).orElseThrow(() -> new EmailContactNotFoundApplicationException(id))); }
}
