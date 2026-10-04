package com.migracion.rangel.infrastructure.airunstatus.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.airunstatus.command.RegisterAiRunStatusCommand;
import com.migracion.rangel.application.airunstatus.command.UpdateAiRunStatusCommand;
import com.migracion.rangel.application.airunstatus.dto.AiRunStatusResponse;
import com.migracion.rangel.application.airunstatus.usecase.RegisterAiRunStatusUseCase;
import com.migracion.rangel.application.airunstatus.usecase.GetAiRunStatusByIdUseCase;
import com.migracion.rangel.application.airunstatus.usecase.ListAiRunStatusUseCase;
import com.migracion.rangel.application.airunstatus.usecase.UpdateAiRunStatusUseCase;
import com.migracion.rangel.application.airunstatus.usecase.DeleteAiRunStatusUseCase;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.infrastructure.airunstatus.adapters.in.rest.dtos.CreateAiRunStatusRequest;
import com.migracion.rangel.infrastructure.airunstatus.adapters.in.rest.dtos.UpdateAiRunStatusRequest;
@RestController
@RequestMapping("/api/ai-run-statuses")
public class AiRunStatusController {
    private final RegisterAiRunStatusUseCase register;
    private final GetAiRunStatusByIdUseCase get;
    private final ListAiRunStatusUseCase list;
    private final UpdateAiRunStatusUseCase update;
    private final DeleteAiRunStatusUseCase delete;
    public AiRunStatusController(RegisterAiRunStatusUseCase register, GetAiRunStatusByIdUseCase get,
            ListAiRunStatusUseCase list, UpdateAiRunStatusUseCase update, DeleteAiRunStatusUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<AiRunStatusResponse> create(@Valid @RequestBody CreateAiRunStatusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterAiRunStatusCommand(request.nameStatus())));
    }
    @GetMapping
    public List<AiRunStatusResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public AiRunStatusResponse findById(@PathVariable("id") UUID id) { return get.execute(new AiRunStatusId(id)); }
    @PutMapping("/{id}")
    public AiRunStatusResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateAiRunStatusRequest request) {
        return update.execute(new UpdateAiRunStatusCommand(new AiRunStatusId(id), request.nameStatus()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new AiRunStatusId(id));
        return ResponseEntity.noContent().build();
    }
}
