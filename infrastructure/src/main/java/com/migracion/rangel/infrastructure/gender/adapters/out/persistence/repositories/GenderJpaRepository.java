package com.migracion.rangel.infrastructure.gender.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.gender.adapters.out.persistence.entity.GenderJpaEntity;
public interface GenderJpaRepository extends JpaRepository<GenderJpaEntity, UUID> {
    boolean existsByDescription(String value);
    boolean existsByDescriptionAndIdNot(String value, UUID id);
}
