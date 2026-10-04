package com.migracion.rangel.application.chatmessage.usecase;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.application.chatmessage.dto.ChatMessageResponse;
import com.migracion.rangel.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatmessage.event.ChatMessageDeletedEvent;
public class DeleteChatMessageUseCase {
    private final ChatMessageRepository repository;
    public DeleteChatMessageUseCase(ChatMessageRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatMessageDeletedEvent execute(ChatMessageId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatMessageNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ChatMessageDeletedEvent(id, LocalDateTime.now());
    }
}

