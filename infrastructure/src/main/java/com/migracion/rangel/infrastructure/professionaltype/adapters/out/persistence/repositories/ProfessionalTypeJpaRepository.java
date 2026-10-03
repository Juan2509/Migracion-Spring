package com.migracion.rangel.infrastructure.professionaltype.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.professionaltype.adapters.out.persistence.entity.ProfessionalTypeJpaEntity;
public interface ProfessionalTypeJpaRepository extends JpaRepository<ProfessionalTypeJpaEntity, UUID> {
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, UUID id);
}
