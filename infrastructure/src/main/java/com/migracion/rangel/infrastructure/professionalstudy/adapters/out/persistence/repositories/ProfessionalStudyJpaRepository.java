package com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.entity.ProfessionalStudyJpaEntity;
public interface ProfessionalStudyJpaRepository extends JpaRepository<ProfessionalStudyJpaEntity, UUID> {}
