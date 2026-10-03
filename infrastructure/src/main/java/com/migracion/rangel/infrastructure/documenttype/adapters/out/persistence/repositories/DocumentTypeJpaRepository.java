package com.migracion.rangel.infrastructure.documenttype.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.documenttype.adapters.out.persistence.entity.DocumentTypeJpaEntity;
public interface DocumentTypeJpaRepository extends JpaRepository<DocumentTypeJpaEntity, UUID> {
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, UUID id);
}
