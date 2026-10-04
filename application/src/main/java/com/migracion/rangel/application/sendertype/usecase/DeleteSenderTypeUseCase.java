package com.migracion.rangel.application.sendertype.usecase;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.application.sendertype.dto.SenderTypeResponse;
import com.migracion.rangel.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.sendertype.event.SenderTypeDeletedEvent;
public class DeleteSenderTypeUseCase {
    private final SenderTypeRepository repository;
    public DeleteSenderTypeUseCase(SenderTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public SenderTypeDeletedEvent execute(SenderTypeId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new SenderTypeNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new SenderTypeDeletedEvent(id, LocalDateTime.now());
    }
}
