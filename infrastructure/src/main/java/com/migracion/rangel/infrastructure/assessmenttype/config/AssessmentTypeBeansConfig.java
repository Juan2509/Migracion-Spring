package com.migracion.rangel.infrastructure.assessmenttype.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import com.migracion.rangel.infrastructure.assessmenttype.adapters.out.persistence.repositories.AssessmentTypeJpaRepository;
import com.migracion.rangel.infrastructure.assessmenttype.adapters.out.persistence.repositories.AssessmentTypeRepositoryAdapter;
import com.migracion.rangel.infrastructure.assessmenttype.adapters.out.persistence.mappers.AssessmentTypePersistenceMapper;
import com.migracion.rangel.application.assessmenttype.usecase.RegisterAssessmentTypeUseCase;
import com.migracion.rangel.application.assessmenttype.usecase.GetAssessmentTypeByIdUseCase;
import com.migracion.rangel.application.assessmenttype.usecase.ListAssessmentTypeUseCase;
import com.migracion.rangel.application.assessmenttype.usecase.UpdateAssessmentTypeUseCase;
import com.migracion.rangel.application.assessmenttype.usecase.DeleteAssessmentTypeUseCase;
@Configuration
public class AssessmentTypeBeansConfig {
    @Bean
    public AssessmentTypePersistenceMapper assessmentTypePersistenceMapper() { return new AssessmentTypePersistenceMapper(); }
    @Bean
    public AssessmentTypeRepository assessmentTypeRepository(AssessmentTypeJpaRepository repository, AssessmentTypePersistenceMapper mapper) {
        return new AssessmentTypeRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterAssessmentTypeUseCase registerAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        return new RegisterAssessmentTypeUseCase(repository);
    }
    @Bean
    public GetAssessmentTypeByIdUseCase getAssessmentTypeByIdUseCase(AssessmentTypeRepository repository) {
        return new GetAssessmentTypeByIdUseCase(repository);
    }
    @Bean
    public ListAssessmentTypeUseCase listAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        return new ListAssessmentTypeUseCase(repository);
    }
    @Bean
    public UpdateAssessmentTypeUseCase updateAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        return new UpdateAssessmentTypeUseCase(repository);
    }
    @Bean
    public DeleteAssessmentTypeUseCase deleteAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        return new DeleteAssessmentTypeUseCase(repository);
    }
}

