package com.migracion.rangel.application.providermodelai.usecase;
import com.migracion.rangel.domain.providermodelai.port.repository.ProviderModelAiRepository;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
import com.migracion.rangel.application.providermodelai.dto.ProviderModelAiResponse;
import com.migracion.rangel.application.providermodelai.exception.ProviderModelAiNotFoundApplicationException;
import java.util.List;
public class ListProviderModelAiUseCase {
    private final ProviderModelAiRepository repository;
    public ListProviderModelAiUseCase(ProviderModelAiRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ProviderModelAiResponse> execute() { return repository.findAll().stream().map(ProviderModelAiResponse::from).toList(); }
}

