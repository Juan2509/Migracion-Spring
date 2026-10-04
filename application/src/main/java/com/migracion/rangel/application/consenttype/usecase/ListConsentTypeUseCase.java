package com.migracion.rangel.application.consenttype.usecase;
import com.migracion.rangel.domain.consenttype.port.repository.ConsentTypeRepository;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
import com.migracion.rangel.application.consenttype.dto.ConsentTypeResponse;
import com.migracion.rangel.application.consenttype.exception.ConsentTypeNotFoundApplicationException;
import java.util.List;
public class ListConsentTypeUseCase {
    private final ConsentTypeRepository repository;
    public ListConsentTypeUseCase(ConsentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ConsentTypeResponse> execute() { return repository.findAll().stream().map(ConsentTypeResponse::from).toList(); }
}

