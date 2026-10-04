package com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.treatmentgoalstatus.command.RegisterTreatmentGoalStatusCommand;
import com.migracion.rangel.application.treatmentgoalstatus.command.UpdateTreatmentGoalStatusCommand;
import com.migracion.rangel.application.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.RegisterTreatmentGoalStatusUseCase;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.GetTreatmentGoalStatusByIdUseCase;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.ListTreatmentGoalStatusUseCase;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.UpdateTreatmentGoalStatusUseCase;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.DeleteTreatmentGoalStatusUseCase;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.in.rest.dtos.CreateTreatmentGoalStatusRequest;
import com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.in.rest.dtos.UpdateTreatmentGoalStatusRequest;
@RestController
@RequestMapping("/api/treatment-goal-statuses")
public class TreatmentGoalStatusController {
    private final RegisterTreatmentGoalStatusUseCase register;
    private final GetTreatmentGoalStatusByIdUseCase get;
    private final ListTreatmentGoalStatusUseCase list;
    private final UpdateTreatmentGoalStatusUseCase update;
    private final DeleteTreatmentGoalStatusUseCase delete;
    public TreatmentGoalStatusController(RegisterTreatmentGoalStatusUseCase register, GetTreatmentGoalStatusByIdUseCase get,
            ListTreatmentGoalStatusUseCase list, UpdateTreatmentGoalStatusUseCase update, DeleteTreatmentGoalStatusUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<TreatmentGoalStatusResponse> create(@Valid @RequestBody CreateTreatmentGoalStatusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterTreatmentGoalStatusCommand(request.code(), request.name(), request.active())));
    }
    @GetMapping
    public List<TreatmentGoalStatusResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public TreatmentGoalStatusResponse findById(@PathVariable("id") UUID id) { return get.execute(new TreatmentGoalStatusId(id)); }
    @PutMapping("/{id}")
    public TreatmentGoalStatusResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateTreatmentGoalStatusRequest request) {
        return update.execute(new UpdateTreatmentGoalStatusCommand(new TreatmentGoalStatusId(id), request.code(), request.name(), request.active()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new TreatmentGoalStatusId(id));
        return ResponseEntity.noContent().build();
    }
}



