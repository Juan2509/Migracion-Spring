package com.migracion.rangel.infrastructure.mentalstatusexam.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.mentalstatusexam.adapters.out.persistence.entity.MentalStatusExamJpaEntity;
public interface MentalStatusExamJpaRepository extends JpaRepository<MentalStatusExamJpaEntity, UUID> {

}

