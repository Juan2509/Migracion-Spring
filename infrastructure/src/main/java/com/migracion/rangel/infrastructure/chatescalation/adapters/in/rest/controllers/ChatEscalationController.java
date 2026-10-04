package com.migracion.rangel.infrastructure.chatescalation.adapters.in.rest.controllers;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.chatescalation.command.RegisterChatEscalationCommand;
import com.migracion.rangel.application.chatescalation.command.UpdateChatEscalationCommand;
import com.migracion.rangel.application.chatescalation.dto.ChatEscalationResponse;
import com.migracion.rangel.application.chatescalation.usecase.RegisterChatEscalationUseCase;
import com.migracion.rangel.application.chatescalation.usecase.GetChatEscalationByIdUseCase;
import com.migracion.rangel.application.chatescalation.usecase.ListChatEscalationUseCase;
import com.migracion.rangel.application.chatescalation.usecase.UpdateChatEscalationUseCase;
import com.migracion.rangel.application.chatescalation.usecase.DeleteChatEscalationUseCase;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.infrastructure.chatescalation.adapters.in.rest.dtos.CreateChatEscalationRequest;
import com.migracion.rangel.infrastructure.chatescalation.adapters.in.rest.dtos.UpdateChatEscalationRequest;
@RestController
@RequestMapping("/api/chat-escalations")
public class ChatEscalationController {
    private final RegisterChatEscalationUseCase register;
    private final GetChatEscalationByIdUseCase get;
    private final ListChatEscalationUseCase list;
    private final UpdateChatEscalationUseCase update;
    private final DeleteChatEscalationUseCase delete;
    public ChatEscalationController(RegisterChatEscalationUseCase register, GetChatEscalationByIdUseCase get,
            ListChatEscalationUseCase list, UpdateChatEscalationUseCase update, DeleteChatEscalationUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ChatEscalationResponse> create(@Valid @RequestBody CreateChatEscalationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterChatEscalationCommand(new ChatConversationId(request.conversationId()), request.reason(), new EscalationStatusId(request.statusId()), request.fromAi())));
    }
    @GetMapping
    public List<ChatEscalationResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ChatEscalationResponse findById(@PathVariable("id") UUID id) { return get.execute(new ChatEscalationId(id)); }
    @PutMapping("/{id}")
    public ChatEscalationResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateChatEscalationRequest request) {
        return update.execute(new UpdateChatEscalationCommand(new ChatEscalationId(id), new ChatConversationId(request.conversationId()), request.reason(), new EscalationStatusId(request.statusId()), request.fromAi()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ChatEscalationId(id));
        return ResponseEntity.noContent().build();
    }
}

