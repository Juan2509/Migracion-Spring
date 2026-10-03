package com.migracion.rangel.infrastructure.encountertype.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.encountertype.command.RegisterEncounterTypeCommand;
import com.migracion.rangel.application.encountertype.command.UpdateEncounterTypeCommand;
import com.migracion.rangel.application.encountertype.dto.EncounterTypeResponse;
import com.migracion.rangel.application.encountertype.usecase.RegisterEncounterTypeUseCase;
import com.migracion.rangel.application.encountertype.usecase.GetEncounterTypeByIdUseCase;
import com.migracion.rangel.application.encountertype.usecase.ListEncounterTypeUseCase;
import com.migracion.rangel.application.encountertype.usecase.UpdateEncounterTypeUseCase;
import com.migracion.rangel.application.encountertype.usecase.DeleteEncounterTypeUseCase;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.infrastructure.encountertype.adapters.in.rest.dtos.CreateEncounterTypeRequest;
import com.migracion.rangel.infrastructure.encountertype.adapters.in.rest.dtos.UpdateEncounterTypeRequest;
@RestController
@RequestMapping("/api/encounter-types")
public class EncounterTypeController {
    private final RegisterEncounterTypeUseCase register;
    private final GetEncounterTypeByIdUseCase get;
    private final ListEncounterTypeUseCase list;
    private final UpdateEncounterTypeUseCase update;
    private final DeleteEncounterTypeUseCase delete;
    public EncounterTypeController(RegisterEncounterTypeUseCase register, GetEncounterTypeByIdUseCase get,
            ListEncounterTypeUseCase list, UpdateEncounterTypeUseCase update, DeleteEncounterTypeUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<EncounterTypeResponse> create(@Valid @RequestBody CreateEncounterTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterEncounterTypeCommand(request.code(), request.name(), request.active())));
    }
    @GetMapping
    public List<EncounterTypeResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public EncounterTypeResponse findById(@PathVariable("id") UUID id) { return get.execute(new EncounterTypeId(id)); }
    @PutMapping("/{id}")
    public EncounterTypeResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateEncounterTypeRequest request) {
        return update.execute(new UpdateEncounterTypeCommand(new EncounterTypeId(id), request.code(), request.name(), request.active()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new EncounterTypeId(id));
        return ResponseEntity.noContent().build();
    }
}

