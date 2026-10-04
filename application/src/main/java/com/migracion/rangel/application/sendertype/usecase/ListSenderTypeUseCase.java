package com.migracion.rangel.application.sendertype.usecase;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.application.sendertype.dto.SenderTypeResponse;
import com.migracion.rangel.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import java.util.List;
public class ListSenderTypeUseCase {
    private final SenderTypeRepository repository;
    public ListSenderTypeUseCase(SenderTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<SenderTypeResponse> execute() { return repository.findAll().stream().map(SenderTypeResponse::from).toList(); }
}
