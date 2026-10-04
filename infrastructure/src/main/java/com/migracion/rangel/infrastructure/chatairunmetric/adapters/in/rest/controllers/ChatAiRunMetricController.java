package com.migracion.rangel.infrastructure.chatairunmetric.adapters.in.rest.controllers;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.chatairunmetric.command.RegisterChatAiRunMetricCommand;
import com.migracion.rangel.application.chatairunmetric.command.UpdateChatAiRunMetricCommand;
import com.migracion.rangel.application.chatairunmetric.dto.ChatAiRunMetricResponse;
import com.migracion.rangel.application.chatairunmetric.usecase.RegisterChatAiRunMetricUseCase;
import com.migracion.rangel.application.chatairunmetric.usecase.GetChatAiRunMetricByIdUseCase;
import com.migracion.rangel.application.chatairunmetric.usecase.ListChatAiRunMetricUseCase;
import com.migracion.rangel.application.chatairunmetric.usecase.UpdateChatAiRunMetricUseCase;
import com.migracion.rangel.application.chatairunmetric.usecase.DeleteChatAiRunMetricUseCase;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import com.migracion.rangel.infrastructure.chatairunmetric.adapters.in.rest.dtos.CreateChatAiRunMetricRequest;
import com.migracion.rangel.infrastructure.chatairunmetric.adapters.in.rest.dtos.UpdateChatAiRunMetricRequest;
@RestController
@RequestMapping("/api/chat-ai-run-metrics")
public class ChatAiRunMetricController {
    private final RegisterChatAiRunMetricUseCase register;
    private final GetChatAiRunMetricByIdUseCase get;
    private final ListChatAiRunMetricUseCase list;
    private final UpdateChatAiRunMetricUseCase update;
    private final DeleteChatAiRunMetricUseCase delete;
    public ChatAiRunMetricController(RegisterChatAiRunMetricUseCase register, GetChatAiRunMetricByIdUseCase get,
            ListChatAiRunMetricUseCase list, UpdateChatAiRunMetricUseCase update, DeleteChatAiRunMetricUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ChatAiRunMetricResponse> create(@Valid @RequestBody CreateChatAiRunMetricRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterChatAiRunMetricCommand(new ChatAiRunId(request.aiRunId()), request.promptTokens(), request.completionTokens(), request.totalTokens(), request.cost())));
    }
    @GetMapping
    public List<ChatAiRunMetricResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ChatAiRunMetricResponse findById(@PathVariable("id") UUID id) { return get.execute(new ChatAiRunMetricId(id)); }
    @PutMapping("/{id}")
    public ChatAiRunMetricResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateChatAiRunMetricRequest request) {
        return update.execute(new UpdateChatAiRunMetricCommand(new ChatAiRunMetricId(id), new ChatAiRunId(request.aiRunId()), request.promptTokens(), request.completionTokens(), request.totalTokens(), request.cost()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ChatAiRunMetricId(id));
        return ResponseEntity.noContent().build();
    }
}

