package com.migracion.rangel.application.aimodel.usecase;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.application.aimodel.dto.AiModelResponse;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import java.util.List;
public class ListAiModelUseCase {
    private final AiModelRepository repository;
    public ListAiModelUseCase(AiModelRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<AiModelResponse> execute() { return repository.findAll().stream().map(AiModelResponse::from).toList(); }
}

