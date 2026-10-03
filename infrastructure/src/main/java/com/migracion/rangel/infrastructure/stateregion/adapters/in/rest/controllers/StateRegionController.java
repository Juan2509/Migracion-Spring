package com.migracion.rangel.infrastructure.stateregion.adapters.in.rest.controllers;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.stateregion.command.RegisterStateRegionCommand;
import com.migracion.rangel.application.stateregion.command.UpdateStateRegionCommand;
import com.migracion.rangel.application.stateregion.dto.StateRegionResponse;
import com.migracion.rangel.application.stateregion.usecase.RegisterStateRegionUseCase;
import com.migracion.rangel.application.stateregion.usecase.GetStateRegionByIdUseCase;
import com.migracion.rangel.application.stateregion.usecase.ListStateRegionUseCase;
import com.migracion.rangel.application.stateregion.usecase.UpdateStateRegionUseCase;
import com.migracion.rangel.application.stateregion.usecase.DeleteStateRegionUseCase;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.infrastructure.stateregion.adapters.in.rest.dtos.CreateStateRegionRequest;
import com.migracion.rangel.infrastructure.stateregion.adapters.in.rest.dtos.UpdateStateRegionRequest;

@RestController
@RequestMapping("/api/state-regions")
public class StateRegionController {
    private final RegisterStateRegionUseCase register;
    private final GetStateRegionByIdUseCase get;
    private final ListStateRegionUseCase list;
    private final UpdateStateRegionUseCase update;
    private final DeleteStateRegionUseCase delete;

    public StateRegionController(RegisterStateRegionUseCase register, GetStateRegionByIdUseCase get,
            ListStateRegionUseCase list, UpdateStateRegionUseCase update, DeleteStateRegionUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<StateRegionResponse> create(@Valid @RequestBody CreateStateRegionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                register.execute(new RegisterStateRegionCommand(request.nameRegion(), request.codeRegion(), request.description(), request.isActive(), new CountryId(request.countryId()))));
    }
    @GetMapping
    public List<StateRegionResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public StateRegionResponse findById(@PathVariable("id") UUID id) { return get.execute(new StateRegionId(id)); }
    @PutMapping("/{id}")
    public StateRegionResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateStateRegionRequest request) {
        return update.execute(new UpdateStateRegionCommand(new StateRegionId(id), request.nameRegion(), request.codeRegion(), request.description(), request.isActive(), new CountryId(request.countryId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new StateRegionId(id));
        return ResponseEntity.noContent().build();
    }
}
