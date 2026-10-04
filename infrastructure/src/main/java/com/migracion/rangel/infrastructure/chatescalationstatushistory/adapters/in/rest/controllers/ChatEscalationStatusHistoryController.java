package com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.in.rest.controllers;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.util.UUID;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.chatescalationstatushistory.command.RegisterChatEscalationStatusHistoryCommand;
import com.migracion.rangel.application.chatescalationstatushistory.command.UpdateChatEscalationStatusHistoryCommand;
import com.migracion.rangel.application.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.RegisterChatEscalationStatusHistoryUseCase;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.GetChatEscalationStatusHistoryByIdUseCase;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.ListChatEscalationStatusHistoryUseCase;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.UpdateChatEscalationStatusHistoryUseCase;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.DeleteChatEscalationStatusHistoryUseCase;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.in.rest.dtos.CreateChatEscalationStatusHistoryRequest;
import com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.in.rest.dtos.UpdateChatEscalationStatusHistoryRequest;
@RestController
@RequestMapping("/api/chat-escalation-status-history")
public class ChatEscalationStatusHistoryController {
    private final RegisterChatEscalationStatusHistoryUseCase register;
    private final GetChatEscalationStatusHistoryByIdUseCase get;
    private final ListChatEscalationStatusHistoryUseCase list;
    private final UpdateChatEscalationStatusHistoryUseCase update;
    private final DeleteChatEscalationStatusHistoryUseCase delete;
    public ChatEscalationStatusHistoryController(RegisterChatEscalationStatusHistoryUseCase register, GetChatEscalationStatusHistoryByIdUseCase get,
            ListChatEscalationStatusHistoryUseCase list, UpdateChatEscalationStatusHistoryUseCase update, DeleteChatEscalationStatusHistoryUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ChatEscalationStatusHistoryResponse> create(@Valid @RequestBody CreateChatEscalationStatusHistoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterChatEscalationStatusHistoryCommand(new ChatEscalationId(request.escalationId()), new EscalationStatusId(request.escalationStatusId()), request.changedAt())));
    }
    @GetMapping
    public List<ChatEscalationStatusHistoryResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ChatEscalationStatusHistoryResponse findById(@PathVariable("id") UUID id) { return get.execute(new ChatEscalationStatusHistoryId(id)); }
    @PutMapping("/{id}")
    public ChatEscalationStatusHistoryResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateChatEscalationStatusHistoryRequest request) {
        return update.execute(new UpdateChatEscalationStatusHistoryCommand(new ChatEscalationStatusHistoryId(id), new ChatEscalationId(request.escalationId()), new EscalationStatusId(request.escalationStatusId()), request.changedAt()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ChatEscalationStatusHistoryId(id));
        return ResponseEntity.noContent().build();
    }
}

