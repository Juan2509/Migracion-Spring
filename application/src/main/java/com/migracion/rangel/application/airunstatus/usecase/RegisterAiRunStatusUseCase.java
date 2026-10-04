package com.migracion.rangel.application.airunstatus.usecase;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.application.airunstatus.dto.AiRunStatusResponse;
import com.migracion.rangel.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import com.migracion.rangel.application.airunstatus.command.RegisterAiRunStatusCommand;
import com.migracion.rangel.domain.airunstatus.model.aggregate.AiRunStatus;
public class RegisterAiRunStatusUseCase {
    private final AiRunStatusRepository repository;
    public RegisterAiRunStatusUseCase(AiRunStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AiRunStatusResponse execute(RegisterAiRunStatusCommand command) {
        var aggregate = AiRunStatus.register(command.nameStatus());
        return AiRunStatusResponse.from(repository.save(aggregate));
    }
}
