package com.migracion.rangel.application.chatconversation.usecase;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.application.chatconversation.dto.ChatConversationResponse;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;

public class GetChatConversationByIdUseCase {
    private final ChatConversationRepository repository;
    public GetChatConversationByIdUseCase(ChatConversationRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatConversationResponse execute(ChatConversationId id) { return ChatConversationResponse.from(repository.findById(id).orElseThrow(() -> new ChatConversationNotFoundApplicationException(id))); }
}

