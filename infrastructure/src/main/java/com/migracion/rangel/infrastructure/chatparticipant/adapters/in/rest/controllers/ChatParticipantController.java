package com.migracion.rangel.infrastructure.chatparticipant.adapters.in.rest.controllers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.chatparticipant.command.RegisterChatParticipantCommand;
import com.migracion.rangel.application.chatparticipant.command.UpdateChatParticipantCommand;
import com.migracion.rangel.application.chatparticipant.dto.ChatParticipantResponse;
import com.migracion.rangel.application.chatparticipant.usecase.RegisterChatParticipantUseCase;
import com.migracion.rangel.application.chatparticipant.usecase.GetChatParticipantByIdUseCase;
import com.migracion.rangel.application.chatparticipant.usecase.ListChatParticipantUseCase;
import com.migracion.rangel.application.chatparticipant.usecase.UpdateChatParticipantUseCase;
import com.migracion.rangel.application.chatparticipant.usecase.DeleteChatParticipantUseCase;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.infrastructure.chatparticipant.adapters.in.rest.dtos.CreateChatParticipantRequest;
import com.migracion.rangel.infrastructure.chatparticipant.adapters.in.rest.dtos.UpdateChatParticipantRequest;
@RestController
@RequestMapping("/api/chat-participants")
public class ChatParticipantController {
    private final RegisterChatParticipantUseCase register;
    private final GetChatParticipantByIdUseCase get;
    private final ListChatParticipantUseCase list;
    private final UpdateChatParticipantUseCase update;
    private final DeleteChatParticipantUseCase delete;
    public ChatParticipantController(RegisterChatParticipantUseCase register, GetChatParticipantByIdUseCase get,
            ListChatParticipantUseCase list, UpdateChatParticipantUseCase update, DeleteChatParticipantUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ChatParticipantResponse> create(@Valid @RequestBody CreateChatParticipantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterChatParticipantCommand(new ChatConversationId(request.conversationId()), new SenderTypeId(request.participantTypeId()), request.patientId() == null ? null : new PatientId(request.patientId()), request.professionalId() == null ? null : new ProfessionalId(request.professionalId()))));
    }
    @GetMapping
    public List<ChatParticipantResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ChatParticipantResponse findById(@PathVariable("id") UUID id) { return get.execute(new ChatParticipantId(id)); }
    @PutMapping("/{id}")
    public ChatParticipantResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateChatParticipantRequest request) {
        return update.execute(new UpdateChatParticipantCommand(new ChatParticipantId(id), new ChatConversationId(request.conversationId()), new SenderTypeId(request.participantTypeId()), request.patientId() == null ? null : new PatientId(request.patientId()), request.professionalId() == null ? null : new ProfessionalId(request.professionalId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ChatParticipantId(id));
        return ResponseEntity.noContent().build();
    }
}

