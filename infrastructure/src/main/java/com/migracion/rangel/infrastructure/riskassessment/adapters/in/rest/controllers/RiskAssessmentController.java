package com.migracion.rangel.infrastructure.riskassessment.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.riskassessment.command.RegisterRiskAssessmentCommand;
import com.migracion.rangel.application.riskassessment.command.UpdateRiskAssessmentCommand;
import com.migracion.rangel.application.riskassessment.dto.RiskAssessmentResponse;
import com.migracion.rangel.application.riskassessment.usecase.RegisterRiskAssessmentUseCase;
import com.migracion.rangel.application.riskassessment.usecase.GetRiskAssessmentByIdUseCase;
import com.migracion.rangel.application.riskassessment.usecase.ListRiskAssessmentUseCase;
import com.migracion.rangel.application.riskassessment.usecase.UpdateRiskAssessmentUseCase;
import com.migracion.rangel.application.riskassessment.usecase.DeleteRiskAssessmentUseCase;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

import com.migracion.rangel.infrastructure.riskassessment.adapters.in.rest.dtos.CreateRiskAssessmentRequest;
import com.migracion.rangel.infrastructure.riskassessment.adapters.in.rest.dtos.UpdateRiskAssessmentRequest;
@RestController
@RequestMapping("/api/risk-assessments")
public class RiskAssessmentController {
    private final RegisterRiskAssessmentUseCase register;
    private final GetRiskAssessmentByIdUseCase get;
    private final ListRiskAssessmentUseCase list;
    private final UpdateRiskAssessmentUseCase update;
    private final DeleteRiskAssessmentUseCase delete;
    public RiskAssessmentController(RegisterRiskAssessmentUseCase register, GetRiskAssessmentByIdUseCase get,
            ListRiskAssessmentUseCase list, UpdateRiskAssessmentUseCase update, DeleteRiskAssessmentUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<RiskAssessmentResponse> create(@Valid @RequestBody CreateRiskAssessmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(
                new RegisterRiskAssessmentCommand(new EncounterId(request.encounterId()), new RiskLevelId(request.riskLevelId()), request.suicidalIdeation(), request.suicidePlan(), request.suicideIntent(), request.selfHarm(), request.harmToOthers(), request.riskFactors(), request.protectiveFactors(), request.clinicalActions(), request.observations(), request.assessedAt(), new ProfessionalId(request.assessedBy()))));
    }
    @GetMapping
    public List<RiskAssessmentResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public RiskAssessmentResponse findById(@PathVariable("id") UUID id) { return get.execute(new RiskAssessmentId(id)); }
    @PutMapping("/{id}")
    public RiskAssessmentResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateRiskAssessmentRequest request) {
        return update.execute(new UpdateRiskAssessmentCommand(new RiskAssessmentId(id), new EncounterId(request.encounterId()), new RiskLevelId(request.riskLevelId()), request.suicidalIdeation(), request.suicidePlan(), request.suicideIntent(), request.selfHarm(), request.harmToOthers(), request.riskFactors(), request.protectiveFactors(), request.clinicalActions(), request.observations(), request.assessedAt(), new ProfessionalId(request.assessedBy())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new RiskAssessmentId(id));
        return ResponseEntity.noContent().build();
    }
}
