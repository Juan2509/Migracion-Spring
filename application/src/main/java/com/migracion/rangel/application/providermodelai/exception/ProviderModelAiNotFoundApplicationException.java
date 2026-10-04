package com.migracion.rangel.application.providermodelai.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.providermodelai.exception.ProviderModelAiNotFoundException;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
public class ProviderModelAiNotFoundApplicationException extends ApplicationException {
    public ProviderModelAiNotFoundApplicationException(ProviderModelAiId id) {
        super("ProviderModelAi no encontrado: " + id.value(), new ProviderModelAiNotFoundException(id));
    }
}

