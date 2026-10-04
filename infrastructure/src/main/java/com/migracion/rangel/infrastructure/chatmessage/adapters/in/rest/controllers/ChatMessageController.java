package com.migracion.rangel.infrastructure.chatmessage.adapters.in.rest.controllers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.infrastructure.chatmessage.adapters.in.rest.dtos.ChatMessageRestResponse;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.chatmessage.command.RegisterChatMessageCommand;
import com.migracion.rangel.application.chatmessage.command.UpdateChatMessageCommand;
import com.migracion.rangel.application.chatmessage.dto.ChatMessageResponse;
import com.migracion.rangel.application.chatmessage.usecase.RegisterChatMessageUseCase;
import com.migracion.rangel.application.chatmessage.usecase.GetChatMessageByIdUseCase;
import com.migracion.rangel.application.chatmessage.usecase.ListChatMessageUseCase;
import com.migracion.rangel.application.chatmessage.usecase.UpdateChatMessageUseCase;
import com.migracion.rangel.application.chatmessage.usecase.DeleteChatMessageUseCase;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.infrastructure.chatmessage.adapters.in.rest.dtos.CreateChatMessageRequest;
import com.migracion.rangel.infrastructure.chatmessage.adapters.in.rest.dtos.UpdateChatMessageRequest;
@RestController
@RequestMapping("/api/chat-messages")
public class ChatMessageController {
    private final RegisterChatMessageUseCase register;
    private final GetChatMessageByIdUseCase get;
    private final ListChatMessageUseCase list;
    private final UpdateChatMessageUseCase update;
    private final DeleteChatMessageUseCase delete;
    public ChatMessageController(RegisterChatMessageUseCase register, GetChatMessageByIdUseCase get,
            ListChatMessageUseCase list, UpdateChatMessageUseCase update, DeleteChatMessageUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ChatMessageRestResponse> create(@Valid @RequestBody CreateChatMessageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ChatMessageRestResponse.from(register.execute(new RegisterChatMessageCommand(new ChatConversationId(request.conversationId()), new MessageTypeId(request.messageTypeId()), new ChatParticipantId(request.participantId()), request.content().toString(), request.metadata().toString()))));
    }
    @GetMapping
    public List<ChatMessageRestResponse> findAll() { return list.execute().stream().map(ChatMessageRestResponse::from).toList(); }
    @GetMapping("/{id}")
    public ChatMessageRestResponse findById(@PathVariable("id") UUID id) { return ChatMessageRestResponse.from(get.execute(new ChatMessageId(id))); }
    @PutMapping("/{id}")
    public ChatMessageRestResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateChatMessageRequest request) {
        return ChatMessageRestResponse.from(update.execute(new UpdateChatMessageCommand(new ChatMessageId(id), new ChatConversationId(request.conversationId()), new MessageTypeId(request.messageTypeId()), new ChatParticipantId(request.participantId()), request.content().toString(), request.metadata().toString())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ChatMessageId(id));
        return ResponseEntity.noContent().build();
    }
}

