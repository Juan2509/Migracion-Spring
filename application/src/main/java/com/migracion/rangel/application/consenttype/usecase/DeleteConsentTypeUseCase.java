package com.migracion.rangel.application.consenttype.usecase;
import com.migracion.rangel.domain.consenttype.port.repository.ConsentTypeRepository;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
import com.migracion.rangel.application.consenttype.dto.ConsentTypeResponse;
import com.migracion.rangel.application.consenttype.exception.ConsentTypeNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.consenttype.event.ConsentTypeDeletedEvent;
public class DeleteConsentTypeUseCase {
    private final ConsentTypeRepository repository;
    public DeleteConsentTypeUseCase(ConsentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ConsentTypeDeletedEvent execute(ConsentTypeId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ConsentTypeNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ConsentTypeDeletedEvent(id, LocalDateTime.now());
    }
}

