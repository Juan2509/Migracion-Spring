package com.migracion.rangel.application.messagetype.usecase;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.application.messagetype.dto.MessageTypeResponse;
import com.migracion.rangel.application.messagetype.exception.MessageTypeNotFoundApplicationException;

public class GetMessageTypeByIdUseCase {
    private final MessageTypeRepository repository;
    public GetMessageTypeByIdUseCase(MessageTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public MessageTypeResponse execute(MessageTypeId id) { return MessageTypeResponse.from(repository.findById(id).orElseThrow(() -> new MessageTypeNotFoundApplicationException(id))); }
}
