package com.migracion.rangel.domain.aimodel.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.aimodel.model.aggregate.AiModel;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
public interface AiModelRepository {
    AiModel save(AiModel aggregate);
    Optional<AiModel> findById(AiModelId id);
    List<AiModel> findAll();
    void delete(AiModel aggregate);
}

