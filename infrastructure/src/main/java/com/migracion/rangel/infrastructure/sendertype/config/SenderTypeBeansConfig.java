package com.migracion.rangel.infrastructure.sendertype.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;
import com.migracion.rangel.infrastructure.sendertype.adapters.out.persistence.repositories.SenderTypeJpaRepository;
import com.migracion.rangel.infrastructure.sendertype.adapters.out.persistence.repositories.SenderTypeRepositoryAdapter;
import com.migracion.rangel.infrastructure.sendertype.adapters.out.persistence.mappers.SenderTypePersistenceMapper;
import com.migracion.rangel.application.sendertype.usecase.RegisterSenderTypeUseCase;
import com.migracion.rangel.application.sendertype.usecase.GetSenderTypeByIdUseCase;
import com.migracion.rangel.application.sendertype.usecase.ListSenderTypeUseCase;
import com.migracion.rangel.application.sendertype.usecase.UpdateSenderTypeUseCase;
import com.migracion.rangel.application.sendertype.usecase.DeleteSenderTypeUseCase;
@Configuration
public class SenderTypeBeansConfig {
    @Bean
    public SenderTypePersistenceMapper sendertypePersistenceMapper() { return new SenderTypePersistenceMapper(); }
    @Bean
    public SenderTypeRepository sendertypeRepository(SenderTypeJpaRepository repository, SenderTypePersistenceMapper mapper) {
        return new SenderTypeRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterSenderTypeUseCase registerSenderTypeUseCase(SenderTypeRepository repository) {
        return new RegisterSenderTypeUseCase(repository);
    }
    @Bean
    public GetSenderTypeByIdUseCase getSenderTypeByIdUseCase(SenderTypeRepository repository) {
        return new GetSenderTypeByIdUseCase(repository);
    }
    @Bean
    public ListSenderTypeUseCase listSenderTypeUseCase(SenderTypeRepository repository) {
        return new ListSenderTypeUseCase(repository);
    }
    @Bean
    public UpdateSenderTypeUseCase updateSenderTypeUseCase(SenderTypeRepository repository) {
        return new UpdateSenderTypeUseCase(repository);
    }
    @Bean
    public DeleteSenderTypeUseCase deleteSenderTypeUseCase(SenderTypeRepository repository) {
        return new DeleteSenderTypeUseCase(repository);
    }
}
