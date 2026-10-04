package com.migracion.rangel.application.riskassessment.usecase;
import com.migracion.rangel.domain.riskassessment.port.repository.RiskAssessmentRepository;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
import com.migracion.rangel.application.riskassessment.dto.RiskAssessmentResponse;
import com.migracion.rangel.application.riskassessment.exception.RiskAssessmentNotFoundApplicationException;


import java.util.List;
public class ListRiskAssessmentUseCase {
    private final RiskAssessmentRepository repository;

    public ListRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public List<RiskAssessmentResponse> execute() { return repository.findAll().stream().map(RiskAssessmentResponse::from).toList(); }
}

