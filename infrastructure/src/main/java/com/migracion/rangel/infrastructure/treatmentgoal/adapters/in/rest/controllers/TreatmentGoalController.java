package com.migracion.rangel.infrastructure.treatmentgoal.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.migracion.rangel.application.treatmentgoal.command.*;
import com.migracion.rangel.application.treatmentgoal.dto.TreatmentGoalResponse;
import com.migracion.rangel.application.treatmentgoal.usecase.*;
import com.migracion.rangel.infrastructure.treatmentgoal.adapters.in.rest.dtos.*;
@RestController
@RequestMapping("/api/treatment-goals")
public class TreatmentGoalController {
    private final RegisterTreatmentGoalUseCase register;
    private final GetTreatmentGoalByIdUseCase get;
    private final ListTreatmentGoalUseCase list;
    private final UpdateTreatmentGoalUseCase update;
    private final DeleteTreatmentGoalUseCase delete;
    public TreatmentGoalController(RegisterTreatmentGoalUseCase register, GetTreatmentGoalByIdUseCase get,
            ListTreatmentGoalUseCase list, UpdateTreatmentGoalUseCase update, DeleteTreatmentGoalUseCase delete) {
        this.register = register; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<TreatmentGoalResponse> create(@Valid @RequestBody CreateTreatmentGoalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterTreatmentGoalCommand(new TreatmentPlanId(request.treatmentPlanId()), request.description(), request.targetDate(), request.completedAt(), request.notes(), new TreatmentGoalStatusId(request.treatmentGoalId()))));
    }
    @GetMapping
    public List<TreatmentGoalResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public TreatmentGoalResponse findById(@PathVariable("id") UUID id) { return get.execute(new TreatmentGoalId(id)); }
    @PutMapping("/{id}")
    public TreatmentGoalResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateTreatmentGoalRequest request) {
        return update.execute(new UpdateTreatmentGoalCommand(new TreatmentGoalId(id), new TreatmentPlanId(request.treatmentPlanId()), request.description(), request.targetDate(), request.completedAt(), request.notes(), new TreatmentGoalStatusId(request.treatmentGoalId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new TreatmentGoalId(id));
        return ResponseEntity.noContent().build();
    }
}

