package com.migracion.rangel.application.sendertype.usecase;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.application.sendertype.dto.SenderTypeResponse;
import com.migracion.rangel.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import com.migracion.rangel.application.sendertype.command.UpdateSenderTypeCommand;
public class UpdateSenderTypeUseCase {
    private final SenderTypeRepository repository;
    public UpdateSenderTypeUseCase(SenderTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public SenderTypeResponse execute(UpdateSenderTypeCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new SenderTypeNotFoundApplicationException(id));
        aggregate.update(command.nameType());
        return SenderTypeResponse.from(repository.save(aggregate));
    }
}
