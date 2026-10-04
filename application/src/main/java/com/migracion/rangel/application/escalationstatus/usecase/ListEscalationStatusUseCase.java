package com.migracion.rangel.application.escalationstatus.usecase;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.application.escalationstatus.dto.EscalationStatusResponse;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import java.util.List;
public class ListEscalationStatusUseCase {
    private final EscalationStatusRepository repository;
    public ListEscalationStatusUseCase(EscalationStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<EscalationStatusResponse> execute() { return repository.findAll().stream().map(EscalationStatusResponse::from).toList(); }
}
