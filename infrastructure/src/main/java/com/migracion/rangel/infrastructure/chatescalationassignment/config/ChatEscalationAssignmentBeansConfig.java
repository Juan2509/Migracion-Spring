package com.migracion.rangel.infrastructure.chatescalationassignment.config;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.repositories.ChatEscalationAssignmentJpaRepository;
import com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.repositories.ChatEscalationAssignmentRepositoryAdapter;
import com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.mappers.ChatEscalationAssignmentPersistenceMapper;
import com.migracion.rangel.application.chatescalationassignment.usecase.RegisterChatEscalationAssignmentUseCase;
import com.migracion.rangel.application.chatescalationassignment.usecase.GetChatEscalationAssignmentByIdUseCase;
import com.migracion.rangel.application.chatescalationassignment.usecase.ListChatEscalationAssignmentUseCase;
import com.migracion.rangel.application.chatescalationassignment.usecase.UpdateChatEscalationAssignmentUseCase;
import com.migracion.rangel.application.chatescalationassignment.usecase.DeleteChatEscalationAssignmentUseCase;
@Configuration
public class ChatEscalationAssignmentBeansConfig {
    @Bean
    public ChatEscalationAssignmentPersistenceMapper chatEscalationAssignmentPersistenceMapper() { return new ChatEscalationAssignmentPersistenceMapper(); }
    @Bean
    public ChatEscalationAssignmentRepository chatEscalationAssignmentRepository(ChatEscalationAssignmentJpaRepository repository, ChatEscalationAssignmentPersistenceMapper mapper) {
        return new ChatEscalationAssignmentRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterChatEscalationAssignmentUseCase registerChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository, ChatEscalationRepository escalations, ProfessionalRepository professionals) {
        return new RegisterChatEscalationAssignmentUseCase(repository, escalations, professionals);
    }
    @Bean
    public GetChatEscalationAssignmentByIdUseCase getChatEscalationAssignmentByIdUseCase(ChatEscalationAssignmentRepository repository) {
        return new GetChatEscalationAssignmentByIdUseCase(repository);
    }
    @Bean
    public ListChatEscalationAssignmentUseCase listChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository) {
        return new ListChatEscalationAssignmentUseCase(repository);
    }
    @Bean
    public UpdateChatEscalationAssignmentUseCase updateChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository, ChatEscalationRepository escalations, ProfessionalRepository professionals) {
        return new UpdateChatEscalationAssignmentUseCase(repository, escalations, professionals);
    }
    @Bean
    public DeleteChatEscalationAssignmentUseCase deleteChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository) {
        return new DeleteChatEscalationAssignmentUseCase(repository);
    }
}

