package com.migracion.rangel.application.priority.usecase;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.application.priority.dto.PriorityResponse;
import com.migracion.rangel.application.priority.exception.PriorityNotFoundApplicationException;
import com.migracion.rangel.application.priority.command.UpdatePriorityCommand;
public class UpdatePriorityUseCase {
    private final PriorityRepository repository;
    public UpdatePriorityUseCase(PriorityRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public PriorityResponse execute(UpdatePriorityCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new PriorityNotFoundApplicationException(id));
        aggregate.update(command.namePriority());
        return PriorityResponse.from(repository.save(aggregate));
    }
}
