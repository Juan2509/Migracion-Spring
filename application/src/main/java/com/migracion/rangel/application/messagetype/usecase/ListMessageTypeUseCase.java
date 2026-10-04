package com.migracion.rangel.application.messagetype.usecase;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.application.messagetype.dto.MessageTypeResponse;
import com.migracion.rangel.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import java.util.List;
public class ListMessageTypeUseCase {
    private final MessageTypeRepository repository;
    public ListMessageTypeUseCase(MessageTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<MessageTypeResponse> execute() { return repository.findAll().stream().map(MessageTypeResponse::from).toList(); }
}
