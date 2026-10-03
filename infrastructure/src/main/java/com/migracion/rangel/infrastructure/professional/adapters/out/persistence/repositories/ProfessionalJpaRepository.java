package com.migracion.rangel.infrastructure.professional.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.professional.adapters.out.persistence.entity.ProfessionalJpaEntity;
public interface ProfessionalJpaRepository extends JpaRepository<ProfessionalJpaEntity, UUID> {
    boolean existsByDocumentNumber(String value);
    boolean existsByDocumentNumberAndIdNot(String value, UUID id);
    boolean existsByFirstName(String value);
    boolean existsByFirstNameAndIdNot(String value, UUID id);
    boolean existsByLastName(String value);
    boolean existsByLastNameAndIdNot(String value, UUID id);
    boolean existsByLicenseNumber(String value);
    boolean existsByLicenseNumberAndIdNot(String value, UUID id);
}
