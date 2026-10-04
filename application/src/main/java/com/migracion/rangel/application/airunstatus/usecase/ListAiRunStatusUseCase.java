package com.migracion.rangel.application.airunstatus.usecase;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.application.airunstatus.dto.AiRunStatusResponse;
import com.migracion.rangel.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import java.util.List;
public class ListAiRunStatusUseCase {
    private final AiRunStatusRepository repository;
    public ListAiRunStatusUseCase(AiRunStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<AiRunStatusResponse> execute() { return repository.findAll().stream().map(AiRunStatusResponse::from).toList(); }
}
