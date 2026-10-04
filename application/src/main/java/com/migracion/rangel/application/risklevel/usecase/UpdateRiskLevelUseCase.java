package com.migracion.rangel.application.risklevel.usecase;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.application.risklevel.dto.RiskLevelResponse;
import com.migracion.rangel.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.migracion.rangel.application.risklevel.exception.DuplicateRiskLevelApplicationException;
import com.migracion.rangel.application.risklevel.command.UpdateRiskLevelCommand;
public class UpdateRiskLevelUseCase {
    private final RiskLevelRepository repository;
    public UpdateRiskLevelUseCase(RiskLevelRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public RiskLevelResponse execute(UpdateRiskLevelCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new RiskLevelNotFoundApplicationException(id));
        if (repository.existsByCodeAndIdNot(command.code(), id)) { throw new DuplicateRiskLevelApplicationException(); }
        aggregate.update(command.code(), command.name(), command.active(), command.severity());
        return RiskLevelResponse.from(repository.save(aggregate));
    }
}

