package com.migracion.rangel.application.phonecontact.usecase;
import com.migracion.rangel.domain.phonecontact.port.repository.PhoneContactRepository;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
import com.migracion.rangel.application.phonecontact.dto.PhoneContactResponse;
import com.migracion.rangel.application.phonecontact.exception.PhoneContactNotFoundApplicationException;


import java.time.LocalDateTime;
import com.migracion.rangel.domain.phonecontact.event.PhoneContactDeletedEvent;
public class DeletePhoneContactUseCase {
    private final PhoneContactRepository repository;

    public DeletePhoneContactUseCase(PhoneContactRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public PhoneContactDeletedEvent execute(PhoneContactId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new PhoneContactNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new PhoneContactDeletedEvent(id, LocalDateTime.now());
    }
}
