package com.migracion.rangel.infrastructure.diagnosticsystem.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.diagnosticsystem.command.RegisterDiagnosticSystemCommand;
import com.migracion.rangel.application.diagnosticsystem.command.UpdateDiagnosticSystemCommand;
import com.migracion.rangel.application.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.migracion.rangel.application.diagnosticsystem.usecase.RegisterDiagnosticSystemUseCase;
import com.migracion.rangel.application.diagnosticsystem.usecase.GetDiagnosticSystemByIdUseCase;
import com.migracion.rangel.application.diagnosticsystem.usecase.ListDiagnosticSystemUseCase;
import com.migracion.rangel.application.diagnosticsystem.usecase.UpdateDiagnosticSystemUseCase;
import com.migracion.rangel.application.diagnosticsystem.usecase.DeleteDiagnosticSystemUseCase;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.migracion.rangel.infrastructure.diagnosticsystem.adapters.in.rest.dtos.CreateDiagnosticSystemRequest;
import com.migracion.rangel.infrastructure.diagnosticsystem.adapters.in.rest.dtos.UpdateDiagnosticSystemRequest;
@RestController
@RequestMapping("/api/diagnostic-systems")
public class DiagnosticSystemController {
    private final RegisterDiagnosticSystemUseCase register;
    private final GetDiagnosticSystemByIdUseCase get;
    private final ListDiagnosticSystemUseCase list;
    private final UpdateDiagnosticSystemUseCase update;
    private final DeleteDiagnosticSystemUseCase delete;
    public DiagnosticSystemController(RegisterDiagnosticSystemUseCase register, GetDiagnosticSystemByIdUseCase get,
            ListDiagnosticSystemUseCase list, UpdateDiagnosticSystemUseCase update, DeleteDiagnosticSystemUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<DiagnosticSystemResponse> create(@Valid @RequestBody CreateDiagnosticSystemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterDiagnosticSystemCommand(request.code(), request.name(), request.active(), request.version())));
    }
    @GetMapping
    public List<DiagnosticSystemResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public DiagnosticSystemResponse findById(@PathVariable("id") UUID id) { return get.execute(new DiagnosticSystemId(id)); }
    @PutMapping("/{id}")
    public DiagnosticSystemResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateDiagnosticSystemRequest request) {
        return update.execute(new UpdateDiagnosticSystemCommand(new DiagnosticSystemId(id), request.code(), request.name(), request.active(), request.version()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new DiagnosticSystemId(id));
        return ResponseEntity.noContent().build();
    }
}

