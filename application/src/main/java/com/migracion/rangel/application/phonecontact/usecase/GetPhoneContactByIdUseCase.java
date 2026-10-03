package com.migracion.rangel.application.phonecontact.usecase;
import com.migracion.rangel.domain.phonecontact.port.repository.PhoneContactRepository;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
import com.migracion.rangel.application.phonecontact.dto.PhoneContactResponse;
import com.migracion.rangel.application.phonecontact.exception.PhoneContactNotFoundApplicationException;



public class GetPhoneContactByIdUseCase {
    private final PhoneContactRepository repository;

    public GetPhoneContactByIdUseCase(PhoneContactRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public PhoneContactResponse execute(PhoneContactId id) { return PhoneContactResponse.from(repository.findById(id).orElseThrow(() -> new PhoneContactNotFoundApplicationException(id))); }
}
