package com.migracion.rangel.application.risklevel.usecase;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.application.risklevel.dto.RiskLevelResponse;
import com.migracion.rangel.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.migracion.rangel.application.risklevel.exception.DuplicateRiskLevelApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.risklevel.event.RiskLevelDeletedEvent;
public class DeleteRiskLevelUseCase {
    private final RiskLevelRepository repository;
    public DeleteRiskLevelUseCase(RiskLevelRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public RiskLevelDeletedEvent execute(RiskLevelId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new RiskLevelNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new RiskLevelDeletedEvent(id, LocalDateTime.now());
    }
}

