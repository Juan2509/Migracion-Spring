package com.migracion.rangel.infrastructure.treatmentplan.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.treatmentplan.adapters.out.persistence.entity.TreatmentPlanJpaEntity;
public interface TreatmentPlanJpaRepository extends JpaRepository<TreatmentPlanJpaEntity, UUID> {}



