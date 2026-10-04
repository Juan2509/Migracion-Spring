package com.migracion.rangel.application.risklevel.usecase;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.application.risklevel.dto.RiskLevelResponse;
import com.migracion.rangel.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.migracion.rangel.application.risklevel.exception.DuplicateRiskLevelApplicationException;
import com.migracion.rangel.application.risklevel.command.RegisterRiskLevelCommand;
import com.migracion.rangel.domain.risklevel.model.aggregate.RiskLevel;
public class RegisterRiskLevelUseCase {
    private final RiskLevelRepository repository;
    public RegisterRiskLevelUseCase(RiskLevelRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public RiskLevelResponse execute(RegisterRiskLevelCommand command) {
        var aggregate = RiskLevel.register(command.code(), command.name(), command.active(), command.severity());
        if (repository.existsByCode(command.code())) { throw new DuplicateRiskLevelApplicationException(); }
        return RiskLevelResponse.from(repository.save(aggregate));
    }
}

