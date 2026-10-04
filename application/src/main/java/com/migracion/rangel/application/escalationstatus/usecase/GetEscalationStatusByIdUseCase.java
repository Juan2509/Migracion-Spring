package com.migracion.rangel.application.escalationstatus.usecase;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.application.escalationstatus.dto.EscalationStatusResponse;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;

public class GetEscalationStatusByIdUseCase {
    private final EscalationStatusRepository repository;
    public GetEscalationStatusByIdUseCase(EscalationStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public EscalationStatusResponse execute(EscalationStatusId id) { return EscalationStatusResponse.from(repository.findById(id).orElseThrow(() -> new EscalationStatusNotFoundApplicationException(id))); }
}
