package com.migracion.rangel.infrastructure.escalationstatus.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.escalationstatus.command.RegisterEscalationStatusCommand;
import com.migracion.rangel.application.escalationstatus.command.UpdateEscalationStatusCommand;
import com.migracion.rangel.application.escalationstatus.dto.EscalationStatusResponse;
import com.migracion.rangel.application.escalationstatus.usecase.RegisterEscalationStatusUseCase;
import com.migracion.rangel.application.escalationstatus.usecase.GetEscalationStatusByIdUseCase;
import com.migracion.rangel.application.escalationstatus.usecase.ListEscalationStatusUseCase;
import com.migracion.rangel.application.escalationstatus.usecase.UpdateEscalationStatusUseCase;
import com.migracion.rangel.application.escalationstatus.usecase.DeleteEscalationStatusUseCase;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.infrastructure.escalationstatus.adapters.in.rest.dtos.CreateEscalationStatusRequest;
import com.migracion.rangel.infrastructure.escalationstatus.adapters.in.rest.dtos.UpdateEscalationStatusRequest;
@RestController
@RequestMapping("/api/escalation-statuses")
public class EscalationStatusController {
    private final RegisterEscalationStatusUseCase register;
    private final GetEscalationStatusByIdUseCase get;
    private final ListEscalationStatusUseCase list;
    private final UpdateEscalationStatusUseCase update;
    private final DeleteEscalationStatusUseCase delete;
    public EscalationStatusController(RegisterEscalationStatusUseCase register, GetEscalationStatusByIdUseCase get,
            ListEscalationStatusUseCase list, UpdateEscalationStatusUseCase update, DeleteEscalationStatusUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<EscalationStatusResponse> create(@Valid @RequestBody CreateEscalationStatusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterEscalationStatusCommand(request.nameStatus())));
    }
    @GetMapping
    public List<EscalationStatusResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public EscalationStatusResponse findById(@PathVariable("id") UUID id) { return get.execute(new EscalationStatusId(id)); }
    @PutMapping("/{id}")
    public EscalationStatusResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateEscalationStatusRequest request) {
        return update.execute(new UpdateEscalationStatusCommand(new EscalationStatusId(id), request.nameStatus()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new EscalationStatusId(id));
        return ResponseEntity.noContent().build();
    }
}
