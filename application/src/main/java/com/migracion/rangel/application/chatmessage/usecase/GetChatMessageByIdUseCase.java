package com.migracion.rangel.application.chatmessage.usecase;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.application.chatmessage.dto.ChatMessageResponse;
import com.migracion.rangel.application.chatmessage.exception.ChatMessageNotFoundApplicationException;

public class GetChatMessageByIdUseCase {
    private final ChatMessageRepository repository;
    public GetChatMessageByIdUseCase(ChatMessageRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatMessageResponse execute(ChatMessageId id) { return ChatMessageResponse.from(repository.findById(id).orElseThrow(() -> new ChatMessageNotFoundApplicationException(id))); }
}

