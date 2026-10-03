package com.migracion.rangel.application.emailcontact.usecase;
import com.migracion.rangel.domain.emailcontact.port.repository.EmailContactRepository;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
import com.migracion.rangel.application.emailcontact.dto.EmailContactResponse;
import com.migracion.rangel.application.emailcontact.exception.EmailContactNotFoundApplicationException;


import java.time.LocalDateTime;
import com.migracion.rangel.domain.emailcontact.event.EmailContactDeletedEvent;
public class DeleteEmailContactUseCase {
    private final EmailContactRepository repository;

    public DeleteEmailContactUseCase(EmailContactRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public EmailContactDeletedEvent execute(EmailContactId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new EmailContactNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new EmailContactDeletedEvent(id, LocalDateTime.now());
    }
}
