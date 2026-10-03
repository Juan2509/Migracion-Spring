package com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.out.persistence.entity.ClinicalRecordStatusJpaEntity;
public interface ClinicalRecordStatusJpaRepository extends JpaRepository<ClinicalRecordStatusJpaEntity, UUID> {
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, UUID id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
