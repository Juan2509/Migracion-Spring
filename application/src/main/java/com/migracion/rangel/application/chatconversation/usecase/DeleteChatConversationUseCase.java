package com.migracion.rangel.application.chatconversation.usecase;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.application.chatconversation.dto.ChatConversationResponse;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatconversation.event.ChatConversationDeletedEvent;
public class DeleteChatConversationUseCase {
    private final ChatConversationRepository repository;
    public DeleteChatConversationUseCase(ChatConversationRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatConversationDeletedEvent execute(ChatConversationId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatConversationNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ChatConversationDeletedEvent(id, LocalDateTime.now());
    }
}

