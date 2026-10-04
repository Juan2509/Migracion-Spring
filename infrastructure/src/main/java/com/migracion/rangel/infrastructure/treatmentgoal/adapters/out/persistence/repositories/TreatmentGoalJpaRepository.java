package com.migracion.rangel.infrastructure.treatmentgoal.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.treatmentgoal.adapters.out.persistence.entity.TreatmentGoalJpaEntity;
public interface TreatmentGoalJpaRepository extends JpaRepository<TreatmentGoalJpaEntity, UUID> {}


