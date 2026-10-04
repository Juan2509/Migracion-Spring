package com.migracion.rangel.application.messagetype.usecase;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.application.messagetype.dto.MessageTypeResponse;
import com.migracion.rangel.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.messagetype.event.MessageTypeDeletedEvent;
public class DeleteMessageTypeUseCase {
    private final MessageTypeRepository repository;
    public DeleteMessageTypeUseCase(MessageTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public MessageTypeDeletedEvent execute(MessageTypeId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new MessageTypeNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new MessageTypeDeletedEvent(id, LocalDateTime.now());
    }
}
