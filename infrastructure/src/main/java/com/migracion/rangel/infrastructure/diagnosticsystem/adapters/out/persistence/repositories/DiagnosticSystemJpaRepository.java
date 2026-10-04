package com.migracion.rangel.infrastructure.diagnosticsystem.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.diagnosticsystem.adapters.out.persistence.entity.DiagnosticSystemJpaEntity;
public interface DiagnosticSystemJpaRepository extends JpaRepository<DiagnosticSystemJpaEntity, UUID> {
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, UUID id);
}

