package com.migracion.rangel.application.airunstatus.usecase;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.application.airunstatus.dto.AiRunStatusResponse;
import com.migracion.rangel.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import com.migracion.rangel.application.airunstatus.command.UpdateAiRunStatusCommand;
public class UpdateAiRunStatusUseCase {
    private final AiRunStatusRepository repository;
    public UpdateAiRunStatusUseCase(AiRunStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AiRunStatusResponse execute(UpdateAiRunStatusCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new AiRunStatusNotFoundApplicationException(id));
        aggregate.update(command.nameStatus());
        return AiRunStatusResponse.from(repository.save(aggregate));
    }
}
