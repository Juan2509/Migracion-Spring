package com.migracion.rangel.infrastructure.professionalstudy.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.professionalstudy.port.repository.ProfessionalStudyRepository;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.repositories.ProfessionalStudyJpaRepository;
import com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.repositories.ProfessionalStudyRepositoryAdapter;
import com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.mappers.ProfessionalStudyPersistenceMapper;
import com.migracion.rangel.application.professionalstudy.usecase.RegisterProfessionalStudyUseCase;
import com.migracion.rangel.application.professionalstudy.usecase.GetProfessionalStudyByIdUseCase;
import com.migracion.rangel.application.professionalstudy.usecase.ListProfessionalStudyUseCase;
import com.migracion.rangel.application.professionalstudy.usecase.UpdateProfessionalStudyUseCase;
import com.migracion.rangel.application.professionalstudy.usecase.DeleteProfessionalStudyUseCase;
@Configuration
public class ProfessionalStudyBeansConfig {
    @Bean
    public ProfessionalStudyPersistenceMapper professionalStudyPersistenceMapper() { return new ProfessionalStudyPersistenceMapper(); }
    @Bean
    public ProfessionalStudyRepository professionalStudyRepository(ProfessionalStudyJpaRepository repository, ProfessionalStudyPersistenceMapper mapper) {
        return new ProfessionalStudyRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterProfessionalStudyUseCase registerProfessionalStudyUseCase(ProfessionalStudyRepository repository, StudyRepository studies, ProfessionalRepository professionals, CountryRepository countries) {
        return new RegisterProfessionalStudyUseCase(repository, studies, professionals, countries);
    }
    @Bean
    public GetProfessionalStudyByIdUseCase getProfessionalStudyByIdUseCase(ProfessionalStudyRepository repository) {
        return new GetProfessionalStudyByIdUseCase(repository);
    }
    @Bean
    public ListProfessionalStudyUseCase listProfessionalStudyUseCase(ProfessionalStudyRepository repository) {
        return new ListProfessionalStudyUseCase(repository);
    }
    @Bean
    public UpdateProfessionalStudyUseCase updateProfessionalStudyUseCase(ProfessionalStudyRepository repository, StudyRepository studies, ProfessionalRepository professionals, CountryRepository countries) {
        return new UpdateProfessionalStudyUseCase(repository, studies, professionals, countries);
    }
    @Bean
    public DeleteProfessionalStudyUseCase deleteProfessionalStudyUseCase(ProfessionalStudyRepository repository) {
        return new DeleteProfessionalStudyUseCase(repository);
    }
}
