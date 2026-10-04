package com.migracion.rangel.application.priority.usecase;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.application.priority.dto.PriorityResponse;
import com.migracion.rangel.application.priority.exception.PriorityNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.priority.event.PriorityDeletedEvent;
public class DeletePriorityUseCase {
    private final PriorityRepository repository;
    public DeletePriorityUseCase(PriorityRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public PriorityDeletedEvent execute(PriorityId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new PriorityNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new PriorityDeletedEvent(id, LocalDateTime.now());
    }
}
