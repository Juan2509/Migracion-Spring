package com.migracion.rangel.infrastructure.diagnosticsystem.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;
import com.migracion.rangel.infrastructure.diagnosticsystem.adapters.out.persistence.repositories.DiagnosticSystemJpaRepository;
import com.migracion.rangel.infrastructure.diagnosticsystem.adapters.out.persistence.repositories.DiagnosticSystemRepositoryAdapter;
import com.migracion.rangel.infrastructure.diagnosticsystem.adapters.out.persistence.mappers.DiagnosticSystemPersistenceMapper;
import com.migracion.rangel.application.diagnosticsystem.usecase.RegisterDiagnosticSystemUseCase;
import com.migracion.rangel.application.diagnosticsystem.usecase.GetDiagnosticSystemByIdUseCase;
import com.migracion.rangel.application.diagnosticsystem.usecase.ListDiagnosticSystemUseCase;
import com.migracion.rangel.application.diagnosticsystem.usecase.UpdateDiagnosticSystemUseCase;
import com.migracion.rangel.application.diagnosticsystem.usecase.DeleteDiagnosticSystemUseCase;
@Configuration
public class DiagnosticSystemBeansConfig {
    @Bean
    public DiagnosticSystemPersistenceMapper diagnosticSystemPersistenceMapper() { return new DiagnosticSystemPersistenceMapper(); }
    @Bean
    public DiagnosticSystemRepository diagnosticSystemRepository(DiagnosticSystemJpaRepository repository, DiagnosticSystemPersistenceMapper mapper) {
        return new DiagnosticSystemRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterDiagnosticSystemUseCase registerDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        return new RegisterDiagnosticSystemUseCase(repository);
    }
    @Bean
    public GetDiagnosticSystemByIdUseCase getDiagnosticSystemByIdUseCase(DiagnosticSystemRepository repository) {
        return new GetDiagnosticSystemByIdUseCase(repository);
    }
    @Bean
    public ListDiagnosticSystemUseCase listDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        return new ListDiagnosticSystemUseCase(repository);
    }
    @Bean
    public UpdateDiagnosticSystemUseCase updateDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        return new UpdateDiagnosticSystemUseCase(repository);
    }
    @Bean
    public DeleteDiagnosticSystemUseCase deleteDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        return new DeleteDiagnosticSystemUseCase(repository);
    }
}

