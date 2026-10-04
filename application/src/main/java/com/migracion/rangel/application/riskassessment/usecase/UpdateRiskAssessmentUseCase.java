package com.migracion.rangel.application.riskassessment.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.riskassessment.model.aggregate.RiskAssessment;
import com.migracion.rangel.domain.riskassessment.port.repository.RiskAssessmentRepository;
import com.migracion.rangel.application.riskassessment.command.UpdateRiskAssessmentCommand;
import com.migracion.rangel.application.riskassessment.dto.RiskAssessmentResponse;
import com.migracion.rangel.application.riskassessment.exception.RiskAssessmentNotFoundApplicationException;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
import com.migracion.rangel.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
public class UpdateRiskAssessmentUseCase {
    private final RiskAssessmentRepository repository;
    private final EncounterRepository encounters;
    private final RiskLevelRepository levels;
    private final ProfessionalRepository professionals;
    public UpdateRiskAssessmentUseCase(RiskAssessmentRepository repository, EncounterRepository encounters, RiskLevelRepository levels, ProfessionalRepository professionals) {
        this.repository = Objects.requireNonNull(repository);
        this.encounters = Objects.requireNonNull(encounters);
        this.levels = Objects.requireNonNull(levels);
        this.professionals = Objects.requireNonNull(professionals);
    }
    public RiskAssessmentResponse execute(UpdateRiskAssessmentCommand command) {
        var aggregate = repository.findById(command.id()).orElseThrow(() -> new RiskAssessmentNotFoundApplicationException(command.id()));
        encounters.findById(command.encounterId()).orElseThrow(() -> new EncounterNotFoundApplicationException(command.encounterId()));
        levels.findById(command.riskLevelId()).orElseThrow(() -> new RiskLevelNotFoundApplicationException(command.riskLevelId()));
        professionals.findById(command.assessedBy()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.assessedBy()));
        aggregate.update(command.encounterId(), command.riskLevelId(), command.suicidalIdeation(), command.suicidePlan(), command.suicideIntent(), command.selfHarm(), command.harmToOthers(), command.riskFactors(), command.protectiveFactors(), command.clinicalActions(), command.observations(), command.assessedAt(), command.assessedBy());
        return RiskAssessmentResponse.from(repository.save(aggregate));
    }
}
