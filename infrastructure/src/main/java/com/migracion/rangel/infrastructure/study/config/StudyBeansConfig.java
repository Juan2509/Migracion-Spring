package com.migracion.rangel.infrastructure.study.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;
import com.migracion.rangel.infrastructure.study.adapters.out.persistence.repositories.StudyJpaRepository;
import com.migracion.rangel.infrastructure.study.adapters.out.persistence.repositories.StudyRepositoryAdapter;
import com.migracion.rangel.infrastructure.study.adapters.out.persistence.mappers.StudyPersistenceMapper;
import com.migracion.rangel.application.study.usecase.RegisterStudyUseCase;
import com.migracion.rangel.application.study.usecase.GetStudyByIdUseCase;
import com.migracion.rangel.application.study.usecase.ListStudyUseCase;
import com.migracion.rangel.application.study.usecase.UpdateStudyUseCase;
import com.migracion.rangel.application.study.usecase.DeleteStudyUseCase;
@Configuration
public class StudyBeansConfig {
    @Bean
    public StudyPersistenceMapper studyPersistenceMapper() { return new StudyPersistenceMapper(); }
    @Bean
    public StudyRepository studyRepository(StudyJpaRepository repository, StudyPersistenceMapper mapper) {
        return new StudyRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterStudyUseCase registerStudyUseCase(StudyRepository repository) {
        return new RegisterStudyUseCase(repository);
    }
    @Bean
    public GetStudyByIdUseCase getStudyByIdUseCase(StudyRepository repository) {
        return new GetStudyByIdUseCase(repository);
    }
    @Bean
    public ListStudyUseCase listStudyUseCase(StudyRepository repository) {
        return new ListStudyUseCase(repository);
    }
    @Bean
    public UpdateStudyUseCase updateStudyUseCase(StudyRepository repository) {
        return new UpdateStudyUseCase(repository);
    }
    @Bean
    public DeleteStudyUseCase deleteStudyUseCase(StudyRepository repository) {
        return new DeleteStudyUseCase(repository);
    }
}
