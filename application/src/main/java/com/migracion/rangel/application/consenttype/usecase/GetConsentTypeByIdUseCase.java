package com.migracion.rangel.application.consenttype.usecase;
import com.migracion.rangel.domain.consenttype.port.repository.ConsentTypeRepository;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
import com.migracion.rangel.application.consenttype.dto.ConsentTypeResponse;
import com.migracion.rangel.application.consenttype.exception.ConsentTypeNotFoundApplicationException;

public class GetConsentTypeByIdUseCase {
    private final ConsentTypeRepository repository;
    public GetConsentTypeByIdUseCase(ConsentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ConsentTypeResponse execute(ConsentTypeId id) { return ConsentTypeResponse.from(repository.findById(id).orElseThrow(() -> new ConsentTypeNotFoundApplicationException(id))); }
}

