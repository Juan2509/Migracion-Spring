package com.migracion.rangel.infrastructure.gender.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;
import com.migracion.rangel.infrastructure.gender.adapters.out.persistence.repositories.GenderJpaRepository;
import com.migracion.rangel.infrastructure.gender.adapters.out.persistence.repositories.GenderRepositoryAdapter;
import com.migracion.rangel.infrastructure.gender.adapters.out.persistence.mappers.GenderPersistenceMapper;
import com.migracion.rangel.application.gender.usecase.RegisterGenderUseCase;
import com.migracion.rangel.application.gender.usecase.GetGenderByIdUseCase;
import com.migracion.rangel.application.gender.usecase.ListGenderUseCase;
import com.migracion.rangel.application.gender.usecase.UpdateGenderUseCase;
import com.migracion.rangel.application.gender.usecase.DeleteGenderUseCase;
@Configuration
public class GenderBeansConfig {
    @Bean
    public GenderPersistenceMapper genderPersistenceMapper() { return new GenderPersistenceMapper(); }
    @Bean
    public GenderRepository genderRepository(GenderJpaRepository repository, GenderPersistenceMapper mapper) {
        return new GenderRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterGenderUseCase registerGenderUseCase(GenderRepository repository) {
        return new RegisterGenderUseCase(repository);
    }
    @Bean
    public GetGenderByIdUseCase getGenderByIdUseCase(GenderRepository repository) {
        return new GetGenderByIdUseCase(repository);
    }
    @Bean
    public ListGenderUseCase listGenderUseCase(GenderRepository repository) {
        return new ListGenderUseCase(repository);
    }
    @Bean
    public UpdateGenderUseCase updateGenderUseCase(GenderRepository repository) {
        return new UpdateGenderUseCase(repository);
    }
    @Bean
    public DeleteGenderUseCase deleteGenderUseCase(GenderRepository repository) {
        return new DeleteGenderUseCase(repository);
    }
}
