package com.migracion.rangel.infrastructure.chatparticipant.config;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;
import com.migracion.rangel.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.infrastructure.chatparticipant.adapters.out.persistence.repositories.ChatParticipantJpaRepository;
import com.migracion.rangel.infrastructure.chatparticipant.adapters.out.persistence.repositories.ChatParticipantRepositoryAdapter;
import com.migracion.rangel.infrastructure.chatparticipant.adapters.out.persistence.mappers.ChatParticipantPersistenceMapper;
import com.migracion.rangel.application.chatparticipant.usecase.RegisterChatParticipantUseCase;
import com.migracion.rangel.application.chatparticipant.usecase.GetChatParticipantByIdUseCase;
import com.migracion.rangel.application.chatparticipant.usecase.ListChatParticipantUseCase;
import com.migracion.rangel.application.chatparticipant.usecase.UpdateChatParticipantUseCase;
import com.migracion.rangel.application.chatparticipant.usecase.DeleteChatParticipantUseCase;
@Configuration
public class ChatParticipantBeansConfig {
    @Bean
    public ChatParticipantPersistenceMapper chatParticipantPersistenceMapper() { return new ChatParticipantPersistenceMapper(); }
    @Bean
    public ChatParticipantRepository chatParticipantRepository(ChatParticipantJpaRepository repository, ChatParticipantPersistenceMapper mapper) {
        return new ChatParticipantRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterChatParticipantUseCase registerChatParticipantUseCase(ChatParticipantRepository repository, ChatConversationRepository conversations, SenderTypeRepository types, PatientRepository patients, ProfessionalRepository professionals) {
        return new RegisterChatParticipantUseCase(repository, conversations, types, patients, professionals);
    }
    @Bean
    public GetChatParticipantByIdUseCase getChatParticipantByIdUseCase(ChatParticipantRepository repository) {
        return new GetChatParticipantByIdUseCase(repository);
    }
    @Bean
    public ListChatParticipantUseCase listChatParticipantUseCase(ChatParticipantRepository repository) {
        return new ListChatParticipantUseCase(repository);
    }
    @Bean
    public UpdateChatParticipantUseCase updateChatParticipantUseCase(ChatParticipantRepository repository, ChatConversationRepository conversations, SenderTypeRepository types, PatientRepository patients, ProfessionalRepository professionals) {
        return new UpdateChatParticipantUseCase(repository, conversations, types, patients, professionals);
    }
    @Bean
    public DeleteChatParticipantUseCase deleteChatParticipantUseCase(ChatParticipantRepository repository) {
        return new DeleteChatParticipantUseCase(repository);
    }
}

