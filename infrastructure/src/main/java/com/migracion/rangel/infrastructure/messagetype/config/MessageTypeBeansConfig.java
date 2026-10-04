package com.migracion.rangel.infrastructure.messagetype.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.infrastructure.messagetype.adapters.out.persistence.repositories.MessageTypeJpaRepository;
import com.migracion.rangel.infrastructure.messagetype.adapters.out.persistence.repositories.MessageTypeRepositoryAdapter;
import com.migracion.rangel.infrastructure.messagetype.adapters.out.persistence.mappers.MessageTypePersistenceMapper;
import com.migracion.rangel.application.messagetype.usecase.RegisterMessageTypeUseCase;
import com.migracion.rangel.application.messagetype.usecase.GetMessageTypeByIdUseCase;
import com.migracion.rangel.application.messagetype.usecase.ListMessageTypeUseCase;
import com.migracion.rangel.application.messagetype.usecase.UpdateMessageTypeUseCase;
import com.migracion.rangel.application.messagetype.usecase.DeleteMessageTypeUseCase;
@Configuration
public class MessageTypeBeansConfig {
    @Bean
    public MessageTypePersistenceMapper messagetypePersistenceMapper() { return new MessageTypePersistenceMapper(); }
    @Bean
    public MessageTypeRepository messagetypeRepository(MessageTypeJpaRepository repository, MessageTypePersistenceMapper mapper) {
        return new MessageTypeRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterMessageTypeUseCase registerMessageTypeUseCase(MessageTypeRepository repository) {
        return new RegisterMessageTypeUseCase(repository);
    }
    @Bean
    public GetMessageTypeByIdUseCase getMessageTypeByIdUseCase(MessageTypeRepository repository) {
        return new GetMessageTypeByIdUseCase(repository);
    }
    @Bean
    public ListMessageTypeUseCase listMessageTypeUseCase(MessageTypeRepository repository) {
        return new ListMessageTypeUseCase(repository);
    }
    @Bean
    public UpdateMessageTypeUseCase updateMessageTypeUseCase(MessageTypeRepository repository) {
        return new UpdateMessageTypeUseCase(repository);
    }
    @Bean
    public DeleteMessageTypeUseCase deleteMessageTypeUseCase(MessageTypeRepository repository) {
        return new DeleteMessageTypeUseCase(repository);
    }
}
