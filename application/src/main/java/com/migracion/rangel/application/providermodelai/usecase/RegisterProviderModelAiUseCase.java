package com.migracion.rangel.application.providermodelai.usecase;
import com.migracion.rangel.domain.providermodelai.port.repository.ProviderModelAiRepository;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
import com.migracion.rangel.application.providermodelai.dto.ProviderModelAiResponse;
import com.migracion.rangel.application.providermodelai.exception.ProviderModelAiNotFoundApplicationException;
import com.migracion.rangel.application.providermodelai.command.RegisterProviderModelAiCommand;
import com.migracion.rangel.domain.providermodelai.model.aggregate.ProviderModelAi;
public class RegisterProviderModelAiUseCase {
    private final ProviderModelAiRepository repository;
    public RegisterProviderModelAiUseCase(ProviderModelAiRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ProviderModelAiResponse execute(RegisterProviderModelAiCommand command) {
        var aggregate = ProviderModelAi.register(command.nameProviderAi(), command.razonSocial(), command.isActive(), command.sitioWeb());
        return ProviderModelAiResponse.from(repository.save(aggregate));
    }
}

