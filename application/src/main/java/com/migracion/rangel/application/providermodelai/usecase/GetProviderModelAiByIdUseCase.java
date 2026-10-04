package com.migracion.rangel.application.providermodelai.usecase;
import com.migracion.rangel.domain.providermodelai.port.repository.ProviderModelAiRepository;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
import com.migracion.rangel.application.providermodelai.dto.ProviderModelAiResponse;
import com.migracion.rangel.application.providermodelai.exception.ProviderModelAiNotFoundApplicationException;

public class GetProviderModelAiByIdUseCase {
    private final ProviderModelAiRepository repository;
    public GetProviderModelAiByIdUseCase(ProviderModelAiRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ProviderModelAiResponse execute(ProviderModelAiId id) { return ProviderModelAiResponse.from(repository.findById(id).orElseThrow(() -> new ProviderModelAiNotFoundApplicationException(id))); }
}

