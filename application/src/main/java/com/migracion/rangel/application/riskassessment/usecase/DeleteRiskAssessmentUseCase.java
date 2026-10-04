package com.migracion.rangel.application.riskassessment.usecase;
import com.migracion.rangel.domain.riskassessment.port.repository.RiskAssessmentRepository;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
import com.migracion.rangel.application.riskassessment.dto.RiskAssessmentResponse;
import com.migracion.rangel.application.riskassessment.exception.RiskAssessmentNotFoundApplicationException;


import java.time.LocalDateTime;
import com.migracion.rangel.domain.riskassessment.event.RiskAssessmentDeletedEvent;
public class DeleteRiskAssessmentUseCase {
    private final RiskAssessmentRepository repository;

    public DeleteRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public RiskAssessmentDeletedEvent execute(RiskAssessmentId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new RiskAssessmentNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new RiskAssessmentDeletedEvent(id, LocalDateTime.now());
    }
}

