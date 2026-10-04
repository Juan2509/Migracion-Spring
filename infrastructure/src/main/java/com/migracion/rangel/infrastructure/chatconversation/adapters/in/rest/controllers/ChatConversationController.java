package com.migracion.rangel.infrastructure.chatconversation.adapters.in.rest.controllers;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.chatconversation.command.RegisterChatConversationCommand;
import com.migracion.rangel.application.chatconversation.command.UpdateChatConversationCommand;
import com.migracion.rangel.application.chatconversation.dto.ChatConversationResponse;
import com.migracion.rangel.application.chatconversation.usecase.RegisterChatConversationUseCase;
import com.migracion.rangel.application.chatconversation.usecase.GetChatConversationByIdUseCase;
import com.migracion.rangel.application.chatconversation.usecase.ListChatConversationUseCase;
import com.migracion.rangel.application.chatconversation.usecase.UpdateChatConversationUseCase;
import com.migracion.rangel.application.chatconversation.usecase.DeleteChatConversationUseCase;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.infrastructure.chatconversation.adapters.in.rest.dtos.CreateChatConversationRequest;
import com.migracion.rangel.infrastructure.chatconversation.adapters.in.rest.dtos.UpdateChatConversationRequest;
@RestController
@RequestMapping("/api/chat-conversations")
public class ChatConversationController {
    private final RegisterChatConversationUseCase register;
    private final GetChatConversationByIdUseCase get;
    private final ListChatConversationUseCase list;
    private final UpdateChatConversationUseCase update;
    private final DeleteChatConversationUseCase delete;
    public ChatConversationController(RegisterChatConversationUseCase register, GetChatConversationByIdUseCase get,
            ListChatConversationUseCase list, UpdateChatConversationUseCase update, DeleteChatConversationUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ChatConversationResponse> create(@Valid @RequestBody CreateChatConversationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterChatConversationCommand(new ConversationStatusId(request.conversationStatusId()), new PriorityId(request.priorityId()), request.lastMessageAt(), request.closed(), request.closedAt(), request.closedBy())));
    }
    @GetMapping
    public List<ChatConversationResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ChatConversationResponse findById(@PathVariable("id") UUID id) { return get.execute(new ChatConversationId(id)); }
    @PutMapping("/{id}")
    public ChatConversationResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateChatConversationRequest request) {
        return update.execute(new UpdateChatConversationCommand(new ChatConversationId(id), new ConversationStatusId(request.conversationStatusId()), new PriorityId(request.priorityId()), request.lastMessageAt(), request.closed(), request.closedAt(), request.closedBy()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ChatConversationId(id));
        return ResponseEntity.noContent().build();
    }
}

