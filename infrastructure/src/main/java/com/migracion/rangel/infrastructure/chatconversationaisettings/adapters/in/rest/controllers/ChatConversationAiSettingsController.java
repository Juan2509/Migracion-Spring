package com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.in.rest.controllers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.chatconversationaisettings.command.RegisterChatConversationAiSettingsCommand;
import com.migracion.rangel.application.chatconversationaisettings.command.UpdateChatConversationAiSettingsCommand;
import com.migracion.rangel.application.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.migracion.rangel.application.chatconversationaisettings.usecase.RegisterChatConversationAiSettingsUseCase;
import com.migracion.rangel.application.chatconversationaisettings.usecase.GetChatConversationAiSettingsByIdUseCase;
import com.migracion.rangel.application.chatconversationaisettings.usecase.ListChatConversationAiSettingsUseCase;
import com.migracion.rangel.application.chatconversationaisettings.usecase.UpdateChatConversationAiSettingsUseCase;
import com.migracion.rangel.application.chatconversationaisettings.usecase.DeleteChatConversationAiSettingsUseCase;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.in.rest.dtos.CreateChatConversationAiSettingsRequest;
import com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.in.rest.dtos.UpdateChatConversationAiSettingsRequest;
@RestController
@RequestMapping("/api/chat-conversation-ai-settings")
public class ChatConversationAiSettingsController {
    private final RegisterChatConversationAiSettingsUseCase register;
    private final GetChatConversationAiSettingsByIdUseCase get;
    private final ListChatConversationAiSettingsUseCase list;
    private final UpdateChatConversationAiSettingsUseCase update;
    private final DeleteChatConversationAiSettingsUseCase delete;
    public ChatConversationAiSettingsController(RegisterChatConversationAiSettingsUseCase register, GetChatConversationAiSettingsByIdUseCase get,
            ListChatConversationAiSettingsUseCase list, UpdateChatConversationAiSettingsUseCase update, DeleteChatConversationAiSettingsUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ChatConversationAiSettingsResponse> create(@Valid @RequestBody CreateChatConversationAiSettingsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterChatConversationAiSettingsCommand(new ChatConversationId(request.conversationId()), request.aiEnabled(), new AiModelId(request.defaultModelId()))));
    }
    @GetMapping
    public List<ChatConversationAiSettingsResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ChatConversationAiSettingsResponse findById(@PathVariable("id") UUID id) { return get.execute(new ChatConversationAiSettingsId(id)); }
    @PutMapping("/{id}")
    public ChatConversationAiSettingsResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateChatConversationAiSettingsRequest request) {
        return update.execute(new UpdateChatConversationAiSettingsCommand(new ChatConversationAiSettingsId(id), new ChatConversationId(request.conversationId()), request.aiEnabled(), new AiModelId(request.defaultModelId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ChatConversationAiSettingsId(id));
        return ResponseEntity.noContent().build();
    }
}

