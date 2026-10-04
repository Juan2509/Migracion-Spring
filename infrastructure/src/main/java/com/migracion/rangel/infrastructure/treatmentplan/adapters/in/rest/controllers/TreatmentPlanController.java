package com.migracion.rangel.infrastructure.treatmentplan.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.application.treatmentplan.command.*;
import com.migracion.rangel.application.treatmentplan.dto.TreatmentPlanResponse;
import com.migracion.rangel.application.treatmentplan.usecase.*;
import com.migracion.rangel.infrastructure.treatmentplan.adapters.in.rest.dtos.*;
@RestController
@RequestMapping("/api/treatment-plans")
public class TreatmentPlanController {
    private final RegisterTreatmentPlanUseCase register;
    private final GetTreatmentPlanByIdUseCase get;
    private final ListTreatmentPlanUseCase list;
    private final UpdateTreatmentPlanUseCase update;
    private final DeleteTreatmentPlanUseCase delete;
    public TreatmentPlanController(RegisterTreatmentPlanUseCase register, GetTreatmentPlanByIdUseCase get,
            ListTreatmentPlanUseCase list, UpdateTreatmentPlanUseCase update, DeleteTreatmentPlanUseCase delete) {
        this.register = register; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<TreatmentPlanResponse> create(@Valid @RequestBody CreateTreatmentPlanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterTreatmentPlanCommand(new EncounterId(request.encounterId()), request.title(), request.description(), request.startDate(), request.endDate(), new TreatmentStatusId(request.treatmentStatusId()), new ProfessionalId(request.professionalId()))));
    }
    @GetMapping
    public List<TreatmentPlanResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public TreatmentPlanResponse findById(@PathVariable("id") UUID id) { return get.execute(new TreatmentPlanId(id)); }
    @PutMapping("/{id}")
    public TreatmentPlanResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateTreatmentPlanRequest request) {
        return update.execute(new UpdateTreatmentPlanCommand(new TreatmentPlanId(id), new EncounterId(request.encounterId()), request.title(), request.description(), request.startDate(), request.endDate(), new TreatmentStatusId(request.treatmentStatusId()), new ProfessionalId(request.professionalId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new TreatmentPlanId(id));
        return ResponseEntity.noContent().build();
    }
}


