package com.migracion.rangel.application.phonecontact.usecase;
import com.migracion.rangel.domain.phonecontact.port.repository.PhoneContactRepository;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
import com.migracion.rangel.application.phonecontact.dto.PhoneContactResponse;
import com.migracion.rangel.application.phonecontact.exception.PhoneContactNotFoundApplicationException;


import java.util.List;
public class ListPhoneContactUseCase {
    private final PhoneContactRepository repository;

    public ListPhoneContactUseCase(PhoneContactRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public List<PhoneContactResponse> execute() { return repository.findAll().stream().map(PhoneContactResponse::from).toList(); }
}
