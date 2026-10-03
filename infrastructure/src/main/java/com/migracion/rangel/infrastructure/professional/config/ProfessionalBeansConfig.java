package com.migracion.rangel.infrastructure.professional.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.infrastructure.professional.adapters.out.persistence.repositories.ProfessionalJpaRepository;
import com.migracion.rangel.infrastructure.professional.adapters.out.persistence.repositories.ProfessionalRepositoryAdapter;
import com.migracion.rangel.infrastructure.professional.adapters.out.persistence.mappers.ProfessionalPersistenceMapper;
import com.migracion.rangel.application.professional.usecase.RegisterProfessionalUseCase;
import com.migracion.rangel.application.professional.usecase.GetProfessionalByIdUseCase;
import com.migracion.rangel.application.professional.usecase.ListProfessionalUseCase;
import com.migracion.rangel.application.professional.usecase.UpdateProfessionalUseCase;
import com.migracion.rangel.application.professional.usecase.DeleteProfessionalUseCase;
@Configuration
public class ProfessionalBeansConfig {
    @Bean
    public ProfessionalPersistenceMapper professionalPersistenceMapper() { return new ProfessionalPersistenceMapper(); }
    @Bean
    public ProfessionalRepository professionalRepository(ProfessionalJpaRepository repository, ProfessionalPersistenceMapper mapper) {
        return new ProfessionalRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterProfessionalUseCase registerProfessionalUseCase(ProfessionalRepository repository, DocumentTypeRepository documents, ProfessionalTypeRepository types, CityMunicipalityRepository cities) {
        return new RegisterProfessionalUseCase(repository, documents, types, cities);
    }
    @Bean
    public GetProfessionalByIdUseCase getProfessionalByIdUseCase(ProfessionalRepository repository) {
        return new GetProfessionalByIdUseCase(repository);
    }
    @Bean
    public ListProfessionalUseCase listProfessionalUseCase(ProfessionalRepository repository) {
        return new ListProfessionalUseCase(repository);
    }
    @Bean
    public UpdateProfessionalUseCase updateProfessionalUseCase(ProfessionalRepository repository, DocumentTypeRepository documents, ProfessionalTypeRepository types, CityMunicipalityRepository cities) {
        return new UpdateProfessionalUseCase(repository, documents, types, cities);
    }
    @Bean
    public DeleteProfessionalUseCase deleteProfessionalUseCase(ProfessionalRepository repository) {
        return new DeleteProfessionalUseCase(repository);
    }
}
