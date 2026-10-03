package com.migracion.rangel.infrastructure.encounterstatus.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.encounterstatus.command.RegisterEncounterStatusCommand;
import com.migracion.rangel.application.encounterstatus.command.UpdateEncounterStatusCommand;
import com.migracion.rangel.application.encounterstatus.dto.EncounterStatusResponse;
import com.migracion.rangel.application.encounterstatus.usecase.RegisterEncounterStatusUseCase;
import com.migracion.rangel.application.encounterstatus.usecase.GetEncounterStatusByIdUseCase;
import com.migracion.rangel.application.encounterstatus.usecase.ListEncounterStatusUseCase;
import com.migracion.rangel.application.encounterstatus.usecase.UpdateEncounterStatusUseCase;
import com.migracion.rangel.application.encounterstatus.usecase.DeleteEncounterStatusUseCase;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.infrastructure.encounterstatus.adapters.in.rest.dtos.CreateEncounterStatusRequest;
import com.migracion.rangel.infrastructure.encounterstatus.adapters.in.rest.dtos.UpdateEncounterStatusRequest;
@RestController
@RequestMapping("/api/encounter-statuses")
public class EncounterStatusController {
    private final RegisterEncounterStatusUseCase register;
    private final GetEncounterStatusByIdUseCase get;
    private final ListEncounterStatusUseCase list;
    private final UpdateEncounterStatusUseCase update;
    private final DeleteEncounterStatusUseCase delete;
    public EncounterStatusController(RegisterEncounterStatusUseCase register, GetEncounterStatusByIdUseCase get,
            ListEncounterStatusUseCase list, UpdateEncounterStatusUseCase update, DeleteEncounterStatusUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<EncounterStatusResponse> create(@Valid @RequestBody CreateEncounterStatusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterEncounterStatusCommand(request.code(), request.name(), request.active())));
    }
    @GetMapping
    public List<EncounterStatusResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public EncounterStatusResponse findById(@PathVariable("id") UUID id) { return get.execute(new EncounterStatusId(id)); }
    @PutMapping("/{id}")
    public EncounterStatusResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateEncounterStatusRequest request) {
        return update.execute(new UpdateEncounterStatusCommand(new EncounterStatusId(id), request.code(), request.name(), request.active()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new EncounterStatusId(id));
        return ResponseEntity.noContent().build();
    }
}

