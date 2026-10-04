package com.migracion.rangel.application.providermodelai.usecase;
import com.migracion.rangel.domain.providermodelai.port.repository.ProviderModelAiRepository;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
import com.migracion.rangel.application.providermodelai.dto.ProviderModelAiResponse;
import com.migracion.rangel.application.providermodelai.exception.ProviderModelAiNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.providermodelai.event.ProviderModelAiDeletedEvent;
public class DeleteProviderModelAiUseCase {
    private final ProviderModelAiRepository repository;
    public DeleteProviderModelAiUseCase(ProviderModelAiRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ProviderModelAiDeletedEvent execute(ProviderModelAiId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ProviderModelAiNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ProviderModelAiDeletedEvent(id, LocalDateTime.now());
    }
}

