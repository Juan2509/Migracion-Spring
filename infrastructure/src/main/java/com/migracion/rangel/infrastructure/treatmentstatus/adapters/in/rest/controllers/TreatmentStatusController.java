package com.migracion.rangel.infrastructure.treatmentstatus.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.treatmentstatus.command.RegisterTreatmentStatusCommand;
import com.migracion.rangel.application.treatmentstatus.command.UpdateTreatmentStatusCommand;
import com.migracion.rangel.application.treatmentstatus.dto.TreatmentStatusResponse;
import com.migracion.rangel.application.treatmentstatus.usecase.RegisterTreatmentStatusUseCase;
import com.migracion.rangel.application.treatmentstatus.usecase.GetTreatmentStatusByIdUseCase;
import com.migracion.rangel.application.treatmentstatus.usecase.ListTreatmentStatusUseCase;
import com.migracion.rangel.application.treatmentstatus.usecase.UpdateTreatmentStatusUseCase;
import com.migracion.rangel.application.treatmentstatus.usecase.DeleteTreatmentStatusUseCase;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.infrastructure.treatmentstatus.adapters.in.rest.dtos.CreateTreatmentStatusRequest;
import com.migracion.rangel.infrastructure.treatmentstatus.adapters.in.rest.dtos.UpdateTreatmentStatusRequest;
@RestController
@RequestMapping("/api/treatment-statuses")
public class TreatmentStatusController {
    private final RegisterTreatmentStatusUseCase register;
    private final GetTreatmentStatusByIdUseCase get;
    private final ListTreatmentStatusUseCase list;
    private final UpdateTreatmentStatusUseCase update;
    private final DeleteTreatmentStatusUseCase delete;
    public TreatmentStatusController(RegisterTreatmentStatusUseCase register, GetTreatmentStatusByIdUseCase get,
            ListTreatmentStatusUseCase list, UpdateTreatmentStatusUseCase update, DeleteTreatmentStatusUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<TreatmentStatusResponse> create(@Valid @RequestBody CreateTreatmentStatusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterTreatmentStatusCommand(request.code(), request.name(), request.active())));
    }
    @GetMapping
    public List<TreatmentStatusResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public TreatmentStatusResponse findById(@PathVariable("id") UUID id) { return get.execute(new TreatmentStatusId(id)); }
    @PutMapping("/{id}")
    public TreatmentStatusResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateTreatmentStatusRequest request) {
        return update.execute(new UpdateTreatmentStatusCommand(new TreatmentStatusId(id), request.code(), request.name(), request.active()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new TreatmentStatusId(id));
        return ResponseEntity.noContent().build();
    }
}


