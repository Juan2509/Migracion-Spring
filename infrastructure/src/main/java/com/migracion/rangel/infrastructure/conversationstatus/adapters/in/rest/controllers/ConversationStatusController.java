package com.migracion.rangel.infrastructure.conversationstatus.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.conversationstatus.command.RegisterConversationStatusCommand;
import com.migracion.rangel.application.conversationstatus.command.UpdateConversationStatusCommand;
import com.migracion.rangel.application.conversationstatus.dto.ConversationStatusResponse;
import com.migracion.rangel.application.conversationstatus.usecase.RegisterConversationStatusUseCase;
import com.migracion.rangel.application.conversationstatus.usecase.GetConversationStatusByIdUseCase;
import com.migracion.rangel.application.conversationstatus.usecase.ListConversationStatusUseCase;
import com.migracion.rangel.application.conversationstatus.usecase.UpdateConversationStatusUseCase;
import com.migracion.rangel.application.conversationstatus.usecase.DeleteConversationStatusUseCase;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.infrastructure.conversationstatus.adapters.in.rest.dtos.CreateConversationStatusRequest;
import com.migracion.rangel.infrastructure.conversationstatus.adapters.in.rest.dtos.UpdateConversationStatusRequest;
@RestController
@RequestMapping("/api/conversation-statuses")
public class ConversationStatusController {
    private final RegisterConversationStatusUseCase register;
    private final GetConversationStatusByIdUseCase get;
    private final ListConversationStatusUseCase list;
    private final UpdateConversationStatusUseCase update;
    private final DeleteConversationStatusUseCase delete;
    public ConversationStatusController(RegisterConversationStatusUseCase register, GetConversationStatusByIdUseCase get,
            ListConversationStatusUseCase list, UpdateConversationStatusUseCase update, DeleteConversationStatusUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ConversationStatusResponse> create(@Valid @RequestBody CreateConversationStatusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterConversationStatusCommand(request.nameStatus())));
    }
    @GetMapping
    public List<ConversationStatusResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ConversationStatusResponse findById(@PathVariable("id") UUID id) { return get.execute(new ConversationStatusId(id)); }
    @PutMapping("/{id}")
    public ConversationStatusResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateConversationStatusRequest request) {
        return update.execute(new UpdateConversationStatusCommand(new ConversationStatusId(id), request.nameStatus()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ConversationStatusId(id));
        return ResponseEntity.noContent().build();
    }
}
