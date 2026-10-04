package com.migracion.rangel.application.sendertype.usecase;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.application.sendertype.dto.SenderTypeResponse;
import com.migracion.rangel.application.sendertype.exception.SenderTypeNotFoundApplicationException;

public class GetSenderTypeByIdUseCase {
    private final SenderTypeRepository repository;
    public GetSenderTypeByIdUseCase(SenderTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public SenderTypeResponse execute(SenderTypeId id) { return SenderTypeResponse.from(repository.findById(id).orElseThrow(() -> new SenderTypeNotFoundApplicationException(id))); }
}
