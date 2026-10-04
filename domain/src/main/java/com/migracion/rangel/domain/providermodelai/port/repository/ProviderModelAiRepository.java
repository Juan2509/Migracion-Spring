package com.migracion.rangel.domain.providermodelai.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.providermodelai.model.aggregate.ProviderModelAi;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
public interface ProviderModelAiRepository {
    ProviderModelAi save(ProviderModelAi aggregate);
    Optional<ProviderModelAi> findById(ProviderModelAiId id);
    List<ProviderModelAi> findAll();
    void delete(ProviderModelAi aggregate);
}

