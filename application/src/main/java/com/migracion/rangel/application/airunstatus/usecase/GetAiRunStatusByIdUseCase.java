package com.migracion.rangel.application.airunstatus.usecase;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.application.airunstatus.dto.AiRunStatusResponse;
import com.migracion.rangel.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;

public class GetAiRunStatusByIdUseCase {
    private final AiRunStatusRepository repository;
    public GetAiRunStatusByIdUseCase(AiRunStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AiRunStatusResponse execute(AiRunStatusId id) { return AiRunStatusResponse.from(repository.findById(id).orElseThrow(() -> new AiRunStatusNotFoundApplicationException(id))); }
}
