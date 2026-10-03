package com.migracion.rangel.infrastructure.mentalstatusexam.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.infrastructure.mentalstatusexam.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.mentalstatusexam.adapters.out.persistence.mappers.MentalStatusExamPersistenceMapper;
import com.migracion.rangel.application.mentalstatusexam.usecase.*;
@Configuration
public class MentalStatusExamBeansConfig {
    @Bean public MentalStatusExamPersistenceMapper mentalStatusExamPersistenceMapper() { return new MentalStatusExamPersistenceMapper(); }
    @Bean public MentalStatusExamRepository mentalStatusExamRepository(MentalStatusExamJpaRepository repository, MentalStatusExamPersistenceMapper mapper) {
        return new MentalStatusExamRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterMentalStatusExamUseCase registerMentalStatusExamUseCase(MentalStatusExamRepository repository, EncounterRepository encounters, ProfessionalRepository professionals) {
        return new RegisterMentalStatusExamUseCase(repository, encounters, professionals);
    }
    @Bean public UpdateMentalStatusExamUseCase updateMentalStatusExamUseCase(MentalStatusExamRepository repository, EncounterRepository encounters) {
        return new UpdateMentalStatusExamUseCase(repository, encounters);
    }
    @Bean public GetMentalStatusExamByIdUseCase getMentalStatusExamByIdUseCase(MentalStatusExamRepository repository) { return new GetMentalStatusExamByIdUseCase(repository); }
    @Bean public ListMentalStatusExamUseCase listMentalStatusExamUseCase(MentalStatusExamRepository repository) { return new ListMentalStatusExamUseCase(repository); }
    @Bean public DeleteMentalStatusExamUseCase deleteMentalStatusExamUseCase(MentalStatusExamRepository repository) { return new DeleteMentalStatusExamUseCase(repository); }
}
