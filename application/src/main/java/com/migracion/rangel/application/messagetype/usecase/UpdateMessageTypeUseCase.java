package com.migracion.rangel.application.messagetype.usecase;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.application.messagetype.dto.MessageTypeResponse;
import com.migracion.rangel.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import com.migracion.rangel.application.messagetype.command.UpdateMessageTypeCommand;
public class UpdateMessageTypeUseCase {
    private final MessageTypeRepository repository;
    public UpdateMessageTypeUseCase(MessageTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public MessageTypeResponse execute(UpdateMessageTypeCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new MessageTypeNotFoundApplicationException(id));
        aggregate.update(command.nameType());
        return MessageTypeResponse.from(repository.save(aggregate));
    }
}
