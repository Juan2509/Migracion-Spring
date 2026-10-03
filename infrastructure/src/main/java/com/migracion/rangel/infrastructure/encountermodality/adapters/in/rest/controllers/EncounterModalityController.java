package com.migracion.rangel.infrastructure.encountermodality.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.encountermodality.command.RegisterEncounterModalityCommand;
import com.migracion.rangel.application.encountermodality.command.UpdateEncounterModalityCommand;
import com.migracion.rangel.application.encountermodality.dto.EncounterModalityResponse;
import com.migracion.rangel.application.encountermodality.usecase.RegisterEncounterModalityUseCase;
import com.migracion.rangel.application.encountermodality.usecase.GetEncounterModalityByIdUseCase;
import com.migracion.rangel.application.encountermodality.usecase.ListEncounterModalityUseCase;
import com.migracion.rangel.application.encountermodality.usecase.UpdateEncounterModalityUseCase;
import com.migracion.rangel.application.encountermodality.usecase.DeleteEncounterModalityUseCase;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.infrastructure.encountermodality.adapters.in.rest.dtos.CreateEncounterModalityRequest;
import com.migracion.rangel.infrastructure.encountermodality.adapters.in.rest.dtos.UpdateEncounterModalityRequest;
@RestController
@RequestMapping("/api/encounter-modalities")
public class EncounterModalityController {
    private final RegisterEncounterModalityUseCase register;
    private final GetEncounterModalityByIdUseCase get;
    private final ListEncounterModalityUseCase list;
    private final UpdateEncounterModalityUseCase update;
    private final DeleteEncounterModalityUseCase delete;
    public EncounterModalityController(RegisterEncounterModalityUseCase register, GetEncounterModalityByIdUseCase get,
            ListEncounterModalityUseCase list, UpdateEncounterModalityUseCase update, DeleteEncounterModalityUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<EncounterModalityResponse> create(@Valid @RequestBody CreateEncounterModalityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterEncounterModalityCommand(request.code(), request.name(), request.active())));
    }
    @GetMapping
    public List<EncounterModalityResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public EncounterModalityResponse findById(@PathVariable("id") UUID id) { return get.execute(new EncounterModalityId(id)); }
    @PutMapping("/{id}")
    public EncounterModalityResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateEncounterModalityRequest request) {
        return update.execute(new UpdateEncounterModalityCommand(new EncounterModalityId(id), request.code(), request.name(), request.active()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new EncounterModalityId(id));
        return ResponseEntity.noContent().build();
    }
}

