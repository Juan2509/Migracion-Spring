package com.migracion.rangel.application.messagetype.usecase;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.application.messagetype.dto.MessageTypeResponse;
import com.migracion.rangel.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import com.migracion.rangel.application.messagetype.command.RegisterMessageTypeCommand;
import com.migracion.rangel.domain.messagetype.model.aggregate.MessageType;
public class RegisterMessageTypeUseCase {
    private final MessageTypeRepository repository;
    public RegisterMessageTypeUseCase(MessageTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public MessageTypeResponse execute(RegisterMessageTypeCommand command) {
        var aggregate = MessageType.register(command.nameType());
        return MessageTypeResponse.from(repository.save(aggregate));
    }
}
