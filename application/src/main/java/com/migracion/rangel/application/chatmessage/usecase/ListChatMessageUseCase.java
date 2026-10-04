package com.migracion.rangel.application.chatmessage.usecase;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.application.chatmessage.dto.ChatMessageResponse;
import com.migracion.rangel.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import java.util.List;
public class ListChatMessageUseCase {
    private final ChatMessageRepository repository;
    public ListChatMessageUseCase(ChatMessageRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ChatMessageResponse> execute() { return repository.findAll().stream().map(ChatMessageResponse::from).toList(); }
}

