package com.migracion.rangel.application.risklevel.usecase;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.application.risklevel.dto.RiskLevelResponse;
import com.migracion.rangel.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.migracion.rangel.application.risklevel.exception.DuplicateRiskLevelApplicationException;

public class GetRiskLevelByIdUseCase {
    private final RiskLevelRepository repository;
    public GetRiskLevelByIdUseCase(RiskLevelRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public RiskLevelResponse execute(RiskLevelId id) { return RiskLevelResponse.from(repository.findById(id).orElseThrow(() -> new RiskLevelNotFoundApplicationException(id))); }
}

