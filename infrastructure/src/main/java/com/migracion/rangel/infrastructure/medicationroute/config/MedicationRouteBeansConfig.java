package com.migracion.rangel.infrastructure.medicationroute.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.medicationroute.port.repository.MedicationRouteRepository;
import com.migracion.rangel.infrastructure.medicationroute.adapters.out.persistence.repositories.MedicationRouteJpaRepository;
import com.migracion.rangel.infrastructure.medicationroute.adapters.out.persistence.repositories.MedicationRouteRepositoryAdapter;
import com.migracion.rangel.infrastructure.medicationroute.adapters.out.persistence.mappers.MedicationRoutePersistenceMapper;
import com.migracion.rangel.application.medicationroute.usecase.RegisterMedicationRouteUseCase;
import com.migracion.rangel.application.medicationroute.usecase.GetMedicationRouteByIdUseCase;
import com.migracion.rangel.application.medicationroute.usecase.ListMedicationRouteUseCase;
import com.migracion.rangel.application.medicationroute.usecase.UpdateMedicationRouteUseCase;
import com.migracion.rangel.application.medicationroute.usecase.DeleteMedicationRouteUseCase;
@Configuration
public class MedicationRouteBeansConfig {
    @Bean
    public MedicationRoutePersistenceMapper medicationRoutePersistenceMapper() { return new MedicationRoutePersistenceMapper(); }
    @Bean
    public MedicationRouteRepository medicationRouteRepository(MedicationRouteJpaRepository repository, MedicationRoutePersistenceMapper mapper) {
        return new MedicationRouteRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterMedicationRouteUseCase registerMedicationRouteUseCase(MedicationRouteRepository repository) {
        return new RegisterMedicationRouteUseCase(repository);
    }
    @Bean
    public GetMedicationRouteByIdUseCase getMedicationRouteByIdUseCase(MedicationRouteRepository repository) {
        return new GetMedicationRouteByIdUseCase(repository);
    }
    @Bean
    public ListMedicationRouteUseCase listMedicationRouteUseCase(MedicationRouteRepository repository) {
        return new ListMedicationRouteUseCase(repository);
    }
    @Bean
    public UpdateMedicationRouteUseCase updateMedicationRouteUseCase(MedicationRouteRepository repository) {
        return new UpdateMedicationRouteUseCase(repository);
    }
    @Bean
    public DeleteMedicationRouteUseCase deleteMedicationRouteUseCase(MedicationRouteRepository repository) {
        return new DeleteMedicationRouteUseCase(repository);
    }
}

