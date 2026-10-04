package com.migracion.rangel.application.consenttype.usecase;
import com.migracion.rangel.domain.consenttype.port.repository.ConsentTypeRepository;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
import com.migracion.rangel.application.consenttype.dto.ConsentTypeResponse;
import com.migracion.rangel.application.consenttype.exception.ConsentTypeNotFoundApplicationException;
import com.migracion.rangel.application.consenttype.command.RegisterConsentTypeCommand;
import com.migracion.rangel.domain.consenttype.model.aggregate.ConsentType;
public class RegisterConsentTypeUseCase {
    private final ConsentTypeRepository repository;
    public RegisterConsentTypeUseCase(ConsentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ConsentTypeResponse execute(RegisterConsentTypeCommand command) {
        var aggregate = ConsentType.register(command.code(), command.name(), command.active(), command.description());
        return ConsentTypeResponse.from(repository.save(aggregate));
    }
}

