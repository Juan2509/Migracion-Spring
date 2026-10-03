package com.migracion.rangel.infrastructure.clinicalrecord.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.clinicalrecord.adapters.out.persistence.entity.ClinicalRecordJpaEntity;
public interface ClinicalRecordJpaRepository extends JpaRepository<ClinicalRecordJpaEntity, UUID> {

}
