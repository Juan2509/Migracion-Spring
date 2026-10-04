package com.migracion.rangel.infrastructure.aimodel.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.aimodel.command.RegisterAiModelCommand;
import com.migracion.rangel.application.aimodel.command.UpdateAiModelCommand;
import com.migracion.rangel.application.aimodel.dto.AiModelResponse;
import com.migracion.rangel.application.aimodel.usecase.RegisterAiModelUseCase;
import com.migracion.rangel.application.aimodel.usecase.GetAiModelByIdUseCase;
import com.migracion.rangel.application.aimodel.usecase.ListAiModelUseCase;
import com.migracion.rangel.application.aimodel.usecase.UpdateAiModelUseCase;
import com.migracion.rangel.application.aimodel.usecase.DeleteAiModelUseCase;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.infrastructure.aimodel.adapters.in.rest.dtos.CreateAiModelRequest;
import com.migracion.rangel.infrastructure.aimodel.adapters.in.rest.dtos.UpdateAiModelRequest;
@RestController
@RequestMapping("/api/ai-models")
public class AiModelController {
    private final RegisterAiModelUseCase register;
    private final GetAiModelByIdUseCase get;
    private final ListAiModelUseCase list;
    private final UpdateAiModelUseCase update;
    private final DeleteAiModelUseCase delete;
    public AiModelController(RegisterAiModelUseCase register, GetAiModelByIdUseCase get,
            ListAiModelUseCase list, UpdateAiModelUseCase update, DeleteAiModelUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<AiModelResponse> create(@Valid @RequestBody CreateAiModelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterAiModelCommand(request.providerModelId(), request.nameModel(), request.modelKey(), request.inputTokenPrice(), request.outputTokenPrice(), request.maxTokens(), request.contextWindow(), request.isActive())));
    }
    @GetMapping
    public List<AiModelResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public AiModelResponse findById(@PathVariable("id") UUID id) { return get.execute(new AiModelId(id)); }
    @PutMapping("/{id}")
    public AiModelResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateAiModelRequest request) {
        return update.execute(new UpdateAiModelCommand(new AiModelId(id), request.providerModelId(), request.nameModel(), request.modelKey(), request.inputTokenPrice(), request.outputTokenPrice(), request.maxTokens(), request.contextWindow(), request.isActive()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new AiModelId(id));
        return ResponseEntity.noContent().build();
    }
}

