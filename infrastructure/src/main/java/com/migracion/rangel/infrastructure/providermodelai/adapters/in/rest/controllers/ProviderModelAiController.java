package com.migracion.rangel.infrastructure.providermodelai.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.providermodelai.command.RegisterProviderModelAiCommand;
import com.migracion.rangel.application.providermodelai.command.UpdateProviderModelAiCommand;
import com.migracion.rangel.application.providermodelai.dto.ProviderModelAiResponse;
import com.migracion.rangel.application.providermodelai.usecase.RegisterProviderModelAiUseCase;
import com.migracion.rangel.application.providermodelai.usecase.GetProviderModelAiByIdUseCase;
import com.migracion.rangel.application.providermodelai.usecase.ListProviderModelAiUseCase;
import com.migracion.rangel.application.providermodelai.usecase.UpdateProviderModelAiUseCase;
import com.migracion.rangel.application.providermodelai.usecase.DeleteProviderModelAiUseCase;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
import com.migracion.rangel.infrastructure.providermodelai.adapters.in.rest.dtos.CreateProviderModelAiRequest;
import com.migracion.rangel.infrastructure.providermodelai.adapters.in.rest.dtos.UpdateProviderModelAiRequest;
@RestController
@RequestMapping("/api/provider-models-ai")
public class ProviderModelAiController {
    private final RegisterProviderModelAiUseCase register;
    private final GetProviderModelAiByIdUseCase get;
    private final ListProviderModelAiUseCase list;
    private final UpdateProviderModelAiUseCase update;
    private final DeleteProviderModelAiUseCase delete;
    public ProviderModelAiController(RegisterProviderModelAiUseCase register, GetProviderModelAiByIdUseCase get,
            ListProviderModelAiUseCase list, UpdateProviderModelAiUseCase update, DeleteProviderModelAiUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ProviderModelAiResponse> create(@Valid @RequestBody CreateProviderModelAiRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterProviderModelAiCommand(request.nameProviderAi(), request.razonSocial(), request.isActive(), request.sitioWeb())));
    }
    @GetMapping
    public List<ProviderModelAiResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ProviderModelAiResponse findById(@PathVariable("id") UUID id) { return get.execute(new ProviderModelAiId(id)); }
    @PutMapping("/{id}")
    public ProviderModelAiResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateProviderModelAiRequest request) {
        return update.execute(new UpdateProviderModelAiCommand(new ProviderModelAiId(id), request.nameProviderAi(), request.razonSocial(), request.isActive(), request.sitioWeb()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ProviderModelAiId(id));
        return ResponseEntity.noContent().build();
    }
}

