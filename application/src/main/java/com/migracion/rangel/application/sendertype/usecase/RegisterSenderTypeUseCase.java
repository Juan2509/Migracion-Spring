package com.migracion.rangel.application.sendertype.usecase;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.application.sendertype.dto.SenderTypeResponse;
import com.migracion.rangel.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import com.migracion.rangel.application.sendertype.command.RegisterSenderTypeCommand;
import com.migracion.rangel.domain.sendertype.model.aggregate.SenderType;
public class RegisterSenderTypeUseCase {
    private final SenderTypeRepository repository;
    public RegisterSenderTypeUseCase(SenderTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public SenderTypeResponse execute(RegisterSenderTypeCommand command) {
        var aggregate = SenderType.register(command.nameType());
        return SenderTypeResponse.from(repository.save(aggregate));
    }
}
