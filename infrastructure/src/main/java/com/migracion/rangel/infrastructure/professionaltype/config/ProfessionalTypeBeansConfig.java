package com.migracion.rangel.infrastructure.professionaltype.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import com.migracion.rangel.infrastructure.professionaltype.adapters.out.persistence.repositories.ProfessionalTypeJpaRepository;
import com.migracion.rangel.infrastructure.professionaltype.adapters.out.persistence.repositories.ProfessionalTypeRepositoryAdapter;
import com.migracion.rangel.infrastructure.professionaltype.adapters.out.persistence.mappers.ProfessionalTypePersistenceMapper;
import com.migracion.rangel.application.professionaltype.usecase.RegisterProfessionalTypeUseCase;
import com.migracion.rangel.application.professionaltype.usecase.GetProfessionalTypeByIdUseCase;
import com.migracion.rangel.application.professionaltype.usecase.ListProfessionalTypeUseCase;
import com.migracion.rangel.application.professionaltype.usecase.UpdateProfessionalTypeUseCase;
import com.migracion.rangel.application.professionaltype.usecase.DeleteProfessionalTypeUseCase;
@Configuration
public class ProfessionalTypeBeansConfig {
    @Bean
    public ProfessionalTypePersistenceMapper professionalTypePersistenceMapper() { return new ProfessionalTypePersistenceMapper(); }
    @Bean
    public ProfessionalTypeRepository professionalTypeRepository(ProfessionalTypeJpaRepository repository, ProfessionalTypePersistenceMapper mapper) {
        return new ProfessionalTypeRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterProfessionalTypeUseCase registerProfessionalTypeUseCase(ProfessionalTypeRepository repository) {
        return new RegisterProfessionalTypeUseCase(repository);
    }
    @Bean
    public GetProfessionalTypeByIdUseCase getProfessionalTypeByIdUseCase(ProfessionalTypeRepository repository) {
        return new GetProfessionalTypeByIdUseCase(repository);
    }
    @Bean
    public ListProfessionalTypeUseCase listProfessionalTypeUseCase(ProfessionalTypeRepository repository) {
        return new ListProfessionalTypeUseCase(repository);
    }
    @Bean
    public UpdateProfessionalTypeUseCase updateProfessionalTypeUseCase(ProfessionalTypeRepository repository) {
        return new UpdateProfessionalTypeUseCase(repository);
    }
    @Bean
    public DeleteProfessionalTypeUseCase deleteProfessionalTypeUseCase(ProfessionalTypeRepository repository) {
        return new DeleteProfessionalTypeUseCase(repository);
    }
}
