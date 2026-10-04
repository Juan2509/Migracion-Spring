package com.migracion.rangel.infrastructure.chatairun.adapters.in.rest.controllers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.chatairun.command.RegisterChatAiRunCommand;
import com.migracion.rangel.application.chatairun.command.UpdateChatAiRunCommand;
import com.migracion.rangel.application.chatairun.dto.ChatAiRunResponse;
import com.migracion.rangel.application.chatairun.usecase.RegisterChatAiRunUseCase;
import com.migracion.rangel.application.chatairun.usecase.GetChatAiRunByIdUseCase;
import com.migracion.rangel.application.chatairun.usecase.ListChatAiRunUseCase;
import com.migracion.rangel.application.chatairun.usecase.UpdateChatAiRunUseCase;
import com.migracion.rangel.application.chatairun.usecase.DeleteChatAiRunUseCase;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.infrastructure.chatairun.adapters.in.rest.dtos.CreateChatAiRunRequest;
import com.migracion.rangel.infrastructure.chatairun.adapters.in.rest.dtos.UpdateChatAiRunRequest;
@RestController
@RequestMapping("/api/chat-ai-runs")
public class ChatAiRunController {
    private final RegisterChatAiRunUseCase register;
    private final GetChatAiRunByIdUseCase get;
    private final ListChatAiRunUseCase list;
    private final UpdateChatAiRunUseCase update;
    private final DeleteChatAiRunUseCase delete;
    public ChatAiRunController(RegisterChatAiRunUseCase register, GetChatAiRunByIdUseCase get,
            ListChatAiRunUseCase list, UpdateChatAiRunUseCase update, DeleteChatAiRunUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ChatAiRunResponse> create(@Valid @RequestBody CreateChatAiRunRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterChatAiRunCommand(new ChatConversationId(request.conversationId()), new ChatMessageId(request.messageId()), new AiModelId(request.modelId()), new AiRunStatusId(request.aiRunStatusId()))));
    }
    @GetMapping
    public List<ChatAiRunResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ChatAiRunResponse findById(@PathVariable("id") UUID id) { return get.execute(new ChatAiRunId(id)); }
    @PutMapping("/{id}")
    public ChatAiRunResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateChatAiRunRequest request) {
        return update.execute(new UpdateChatAiRunCommand(new ChatAiRunId(id), new ChatConversationId(request.conversationId()), new ChatMessageId(request.messageId()), new AiModelId(request.modelId()), new AiRunStatusId(request.aiRunStatusId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ChatAiRunId(id));
        return ResponseEntity.noContent().build();
    }
}

