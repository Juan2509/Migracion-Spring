package com.migracion.rangel.infrastructure.risklevel.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
import com.migracion.rangel.infrastructure.risklevel.adapters.out.persistence.repositories.RiskLevelJpaRepository;
import com.migracion.rangel.infrastructure.risklevel.adapters.out.persistence.repositories.RiskLevelRepositoryAdapter;
import com.migracion.rangel.infrastructure.risklevel.adapters.out.persistence.mappers.RiskLevelPersistenceMapper;
import com.migracion.rangel.application.risklevel.usecase.RegisterRiskLevelUseCase;
import com.migracion.rangel.application.risklevel.usecase.GetRiskLevelByIdUseCase;
import com.migracion.rangel.application.risklevel.usecase.ListRiskLevelUseCase;
import com.migracion.rangel.application.risklevel.usecase.UpdateRiskLevelUseCase;
import com.migracion.rangel.application.risklevel.usecase.DeleteRiskLevelUseCase;
@Configuration
public class RiskLevelBeansConfig {
    @Bean
    public RiskLevelPersistenceMapper riskLevelPersistenceMapper() { return new RiskLevelPersistenceMapper(); }
    @Bean
    public RiskLevelRepository riskLevelRepository(RiskLevelJpaRepository repository, RiskLevelPersistenceMapper mapper) {
        return new RiskLevelRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterRiskLevelUseCase registerRiskLevelUseCase(RiskLevelRepository repository) {
        return new RegisterRiskLevelUseCase(repository);
    }
    @Bean
    public GetRiskLevelByIdUseCase getRiskLevelByIdUseCase(RiskLevelRepository repository) {
        return new GetRiskLevelByIdUseCase(repository);
    }
    @Bean
    public ListRiskLevelUseCase listRiskLevelUseCase(RiskLevelRepository repository) {
        return new ListRiskLevelUseCase(repository);
    }
    @Bean
    public UpdateRiskLevelUseCase updateRiskLevelUseCase(RiskLevelRepository repository) {
        return new UpdateRiskLevelUseCase(repository);
    }
    @Bean
    public DeleteRiskLevelUseCase deleteRiskLevelUseCase(RiskLevelRepository repository) {
        return new DeleteRiskLevelUseCase(repository);
    }
}

