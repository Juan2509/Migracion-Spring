package com.migracion.rangel.application.providermodelai.usecase;
import com.migracion.rangel.domain.providermodelai.port.repository.ProviderModelAiRepository;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
import com.migracion.rangel.application.providermodelai.dto.ProviderModelAiResponse;
import com.migracion.rangel.application.providermodelai.exception.ProviderModelAiNotFoundApplicationException;
import com.migracion.rangel.application.providermodelai.command.UpdateProviderModelAiCommand;
public class UpdateProviderModelAiUseCase {
    private final ProviderModelAiRepository repository;
    public UpdateProviderModelAiUseCase(ProviderModelAiRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ProviderModelAiResponse execute(UpdateProviderModelAiCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ProviderModelAiNotFoundApplicationException(id));
        aggregate.update(command.nameProviderAi(), command.razonSocial(), command.isActive(), command.sitioWeb());
        return ProviderModelAiResponse.from(repository.save(aggregate));
    }
}

