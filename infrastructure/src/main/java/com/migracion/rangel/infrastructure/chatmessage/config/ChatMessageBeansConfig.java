package com.migracion.rangel.infrastructure.chatmessage.config;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.repositories.ChatMessageJpaRepository;
import com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.repositories.ChatMessageRepositoryAdapter;
import com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.mappers.ChatMessagePersistenceMapper;
import com.migracion.rangel.application.chatmessage.usecase.RegisterChatMessageUseCase;
import com.migracion.rangel.application.chatmessage.usecase.GetChatMessageByIdUseCase;
import com.migracion.rangel.application.chatmessage.usecase.ListChatMessageUseCase;
import com.migracion.rangel.application.chatmessage.usecase.UpdateChatMessageUseCase;
import com.migracion.rangel.application.chatmessage.usecase.DeleteChatMessageUseCase;
@Configuration
public class ChatMessageBeansConfig {
    @Bean
    public ChatMessagePersistenceMapper chatMessagePersistenceMapper() { return new ChatMessagePersistenceMapper(); }
    @Bean
    public ChatMessageRepository chatMessageRepository(ChatMessageJpaRepository repository, ChatMessagePersistenceMapper mapper) {
        return new ChatMessageRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterChatMessageUseCase registerChatMessageUseCase(ChatMessageRepository repository, ChatConversationRepository conversations, MessageTypeRepository types, ChatParticipantRepository participants) {
        return new RegisterChatMessageUseCase(repository, conversations, types, participants);
    }
    @Bean
    public GetChatMessageByIdUseCase getChatMessageByIdUseCase(ChatMessageRepository repository) {
        return new GetChatMessageByIdUseCase(repository);
    }
    @Bean
    public ListChatMessageUseCase listChatMessageUseCase(ChatMessageRepository repository) {
        return new ListChatMessageUseCase(repository);
    }
    @Bean
    public UpdateChatMessageUseCase updateChatMessageUseCase(ChatMessageRepository repository, ChatConversationRepository conversations, MessageTypeRepository types, ChatParticipantRepository participants) {
        return new UpdateChatMessageUseCase(repository, conversations, types, participants);
    }
    @Bean
    public DeleteChatMessageUseCase deleteChatMessageUseCase(ChatMessageRepository repository) {
        return new DeleteChatMessageUseCase(repository);
    }
}

