package com.migracion.rangel.application.consenttype.usecase;
import com.migracion.rangel.domain.consenttype.port.repository.ConsentTypeRepository;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
import com.migracion.rangel.application.consenttype.dto.ConsentTypeResponse;
import com.migracion.rangel.application.consenttype.exception.ConsentTypeNotFoundApplicationException;
import com.migracion.rangel.application.consenttype.command.UpdateConsentTypeCommand;
public class UpdateConsentTypeUseCase {
    private final ConsentTypeRepository repository;
    public UpdateConsentTypeUseCase(ConsentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ConsentTypeResponse execute(UpdateConsentTypeCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ConsentTypeNotFoundApplicationException(id));
        aggregate.update(command.code(), command.name(), command.active(), command.description());
        return ConsentTypeResponse.from(repository.save(aggregate));
    }
}

