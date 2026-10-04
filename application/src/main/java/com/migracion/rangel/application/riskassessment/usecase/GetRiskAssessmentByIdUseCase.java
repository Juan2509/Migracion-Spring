package com.migracion.rangel.application.riskassessment.usecase;
import com.migracion.rangel.domain.riskassessment.port.repository.RiskAssessmentRepository;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
import com.migracion.rangel.application.riskassessment.dto.RiskAssessmentResponse;
import com.migracion.rangel.application.riskassessment.exception.RiskAssessmentNotFoundApplicationException;



public class GetRiskAssessmentByIdUseCase {
    private final RiskAssessmentRepository repository;

    public GetRiskAssessmentByIdUseCase(RiskAssessmentRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public RiskAssessmentResponse execute(RiskAssessmentId id) { return RiskAssessmentResponse.from(repository.findById(id).orElseThrow(() -> new RiskAssessmentNotFoundApplicationException(id))); }
}

