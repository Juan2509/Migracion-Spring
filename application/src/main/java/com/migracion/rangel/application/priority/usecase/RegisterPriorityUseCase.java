package com.migracion.rangel.application.priority.usecase;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.application.priority.dto.PriorityResponse;
import com.migracion.rangel.application.priority.exception.PriorityNotFoundApplicationException;
import com.migracion.rangel.application.priority.command.RegisterPriorityCommand;
import com.migracion.rangel.domain.priority.model.aggregate.Priority;
public class RegisterPriorityUseCase {
    private final PriorityRepository repository;
    public RegisterPriorityUseCase(PriorityRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public PriorityResponse execute(RegisterPriorityCommand command) {
        var aggregate = Priority.register(command.namePriority());
        return PriorityResponse.from(repository.save(aggregate));
    }
}
