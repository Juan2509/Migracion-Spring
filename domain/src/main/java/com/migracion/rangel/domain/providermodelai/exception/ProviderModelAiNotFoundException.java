package com.migracion.rangel.domain.providermodelai.exception;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
public class ProviderModelAiNotFoundException extends RuntimeException {
    public ProviderModelAiNotFoundException(ProviderModelAiId id) { super("ProviderModelAi no encontrado: " + id.value()); }
}

