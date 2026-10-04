package com.migracion.rangel.infrastructure.chatairunerror.adapters.in.rest.controllers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.chatairunerror.command.RegisterChatAiRunErrorCommand;
import com.migracion.rangel.application.chatairunerror.command.UpdateChatAiRunErrorCommand;
import com.migracion.rangel.application.chatairunerror.dto.ChatAiRunErrorResponse;
import com.migracion.rangel.application.chatairunerror.usecase.RegisterChatAiRunErrorUseCase;
import com.migracion.rangel.application.chatairunerror.usecase.GetChatAiRunErrorByIdUseCase;
import com.migracion.rangel.application.chatairunerror.usecase.ListChatAiRunErrorUseCase;
import com.migracion.rangel.application.chatairunerror.usecase.UpdateChatAiRunErrorUseCase;
import com.migracion.rangel.application.chatairunerror.usecase.DeleteChatAiRunErrorUseCase;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.migracion.rangel.infrastructure.chatairunerror.adapters.in.rest.dtos.CreateChatAiRunErrorRequest;
import com.migracion.rangel.infrastructure.chatairunerror.adapters.in.rest.dtos.UpdateChatAiRunErrorRequest;
@RestController
@RequestMapping("/api/chat-ai-run-errors")
public class ChatAiRunErrorController {
    private final RegisterChatAiRunErrorUseCase register;
    private final GetChatAiRunErrorByIdUseCase get;
    private final ListChatAiRunErrorUseCase list;
    private final UpdateChatAiRunErrorUseCase update;
    private final DeleteChatAiRunErrorUseCase delete;
    public ChatAiRunErrorController(RegisterChatAiRunErrorUseCase register, GetChatAiRunErrorByIdUseCase get,
            ListChatAiRunErrorUseCase list, UpdateChatAiRunErrorUseCase update, DeleteChatAiRunErrorUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ChatAiRunErrorResponse> create(@Valid @RequestBody CreateChatAiRunErrorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterChatAiRunErrorCommand(new ChatAiRunId(request.aiRunId()), request.errorMessage(), request.errorCode(), request.providerErrorId())));
    }
    @GetMapping
    public List<ChatAiRunErrorResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ChatAiRunErrorResponse findById(@PathVariable("id") UUID id) { return get.execute(new ChatAiRunErrorId(id)); }
    @PutMapping("/{id}")
    public ChatAiRunErrorResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateChatAiRunErrorRequest request) {
        return update.execute(new UpdateChatAiRunErrorCommand(new ChatAiRunErrorId(id), new ChatAiRunId(request.aiRunId()), request.errorMessage(), request.errorCode(), request.providerErrorId()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ChatAiRunErrorId(id));
        return ResponseEntity.noContent().build();
    }
}

