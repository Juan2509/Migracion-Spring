package com.migracion.rangel.infrastructure.chatescalationassignment.adapters.in.rest.controllers;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.util.UUID;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.chatescalationassignment.command.RegisterChatEscalationAssignmentCommand;
import com.migracion.rangel.application.chatescalationassignment.command.UpdateChatEscalationAssignmentCommand;
import com.migracion.rangel.application.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import com.migracion.rangel.application.chatescalationassignment.usecase.RegisterChatEscalationAssignmentUseCase;
import com.migracion.rangel.application.chatescalationassignment.usecase.GetChatEscalationAssignmentByIdUseCase;
import com.migracion.rangel.application.chatescalationassignment.usecase.ListChatEscalationAssignmentUseCase;
import com.migracion.rangel.application.chatescalationassignment.usecase.UpdateChatEscalationAssignmentUseCase;
import com.migracion.rangel.application.chatescalationassignment.usecase.DeleteChatEscalationAssignmentUseCase;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.migracion.rangel.infrastructure.chatescalationassignment.adapters.in.rest.dtos.CreateChatEscalationAssignmentRequest;
import com.migracion.rangel.infrastructure.chatescalationassignment.adapters.in.rest.dtos.UpdateChatEscalationAssignmentRequest;
@RestController
@RequestMapping("/api/chat-escalation-assignments")
public class ChatEscalationAssignmentController {
    private final RegisterChatEscalationAssignmentUseCase register;
    private final GetChatEscalationAssignmentByIdUseCase get;
    private final ListChatEscalationAssignmentUseCase list;
    private final UpdateChatEscalationAssignmentUseCase update;
    private final DeleteChatEscalationAssignmentUseCase delete;
    public ChatEscalationAssignmentController(RegisterChatEscalationAssignmentUseCase register, GetChatEscalationAssignmentByIdUseCase get,
            ListChatEscalationAssignmentUseCase list, UpdateChatEscalationAssignmentUseCase update, DeleteChatEscalationAssignmentUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ChatEscalationAssignmentResponse> create(@Valid @RequestBody CreateChatEscalationAssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterChatEscalationAssignmentCommand(new ChatEscalationId(request.escalationId()), new ProfessionalId(request.professionalId()), request.assignedAt())));
    }
    @GetMapping
    public List<ChatEscalationAssignmentResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ChatEscalationAssignmentResponse findById(@PathVariable("id") UUID id) { return get.execute(new ChatEscalationAssignmentId(id)); }
    @PutMapping("/{id}")
    public ChatEscalationAssignmentResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateChatEscalationAssignmentRequest request) {
        return update.execute(new UpdateChatEscalationAssignmentCommand(new ChatEscalationAssignmentId(id), new ChatEscalationId(request.escalationId()), new ProfessionalId(request.professionalId()), request.assignedAt()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ChatEscalationAssignmentId(id));
        return ResponseEntity.noContent().build();
    }
}

