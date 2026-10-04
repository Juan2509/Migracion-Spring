package com.migracion.rangel.application.risklevel.usecase;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.application.risklevel.dto.RiskLevelResponse;
import com.migracion.rangel.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.migracion.rangel.application.risklevel.exception.DuplicateRiskLevelApplicationException;
import java.util.List;
public class ListRiskLevelUseCase {
    private final RiskLevelRepository repository;
    public ListRiskLevelUseCase(RiskLevelRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<RiskLevelResponse> execute() { return repository.findAll().stream().map(RiskLevelResponse::from).toList(); }
}

