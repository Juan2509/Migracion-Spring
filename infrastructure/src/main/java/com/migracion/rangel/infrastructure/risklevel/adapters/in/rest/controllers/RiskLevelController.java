package com.migracion.rangel.infrastructure.risklevel.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.risklevel.command.RegisterRiskLevelCommand;
import com.migracion.rangel.application.risklevel.command.UpdateRiskLevelCommand;
import com.migracion.rangel.application.risklevel.dto.RiskLevelResponse;
import com.migracion.rangel.application.risklevel.usecase.RegisterRiskLevelUseCase;
import com.migracion.rangel.application.risklevel.usecase.GetRiskLevelByIdUseCase;
import com.migracion.rangel.application.risklevel.usecase.ListRiskLevelUseCase;
import com.migracion.rangel.application.risklevel.usecase.UpdateRiskLevelUseCase;
import com.migracion.rangel.application.risklevel.usecase.DeleteRiskLevelUseCase;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.infrastructure.risklevel.adapters.in.rest.dtos.CreateRiskLevelRequest;
import com.migracion.rangel.infrastructure.risklevel.adapters.in.rest.dtos.UpdateRiskLevelRequest;
@RestController
@RequestMapping("/api/risk-levels")
public class RiskLevelController {
    private final RegisterRiskLevelUseCase register;
    private final GetRiskLevelByIdUseCase get;
    private final ListRiskLevelUseCase list;
    private final UpdateRiskLevelUseCase update;
    private final DeleteRiskLevelUseCase delete;
    public RiskLevelController(RegisterRiskLevelUseCase register, GetRiskLevelByIdUseCase get,
            ListRiskLevelUseCase list, UpdateRiskLevelUseCase update, DeleteRiskLevelUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<RiskLevelResponse> create(@Valid @RequestBody CreateRiskLevelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterRiskLevelCommand(request.code(), request.name(), request.active(), request.severity())));
    }
    @GetMapping
    public List<RiskLevelResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public RiskLevelResponse findById(@PathVariable("id") UUID id) { return get.execute(new RiskLevelId(id)); }
    @PutMapping("/{id}")
    public RiskLevelResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateRiskLevelRequest request) {
        return update.execute(new UpdateRiskLevelCommand(new RiskLevelId(id), request.code(), request.name(), request.active(), request.severity()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new RiskLevelId(id));
        return ResponseEntity.noContent().build();
    }
}

