package com.migracion.rangel.application.chatconversation.usecase;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.application.chatconversation.dto.ChatConversationResponse;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import java.util.List;
public class ListChatConversationUseCase {
    private final ChatConversationRepository repository;
    public ListChatConversationUseCase(ChatConversationRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ChatConversationResponse> execute() { return repository.findAll().stream().map(ChatConversationResponse::from).toList(); }
}

