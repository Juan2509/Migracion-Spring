package com.migracion.rangel.domain.professional.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
public interface ProfessionalRepository {
    Professional save(Professional professional);
    Optional<Professional> findById(ProfessionalId id);
    List<Professional> findAll();
    void delete(Professional professional);
    boolean existsByDocumentNumber(String value);
    boolean existsByDocumentNumberAndIdNot(String value, ProfessionalId id);
    boolean existsByFirstName(String value);
    boolean existsByFirstNameAndIdNot(String value, ProfessionalId id);
    boolean existsByLastName(String value);
    boolean existsByLastNameAndIdNot(String value, ProfessionalId id);
    boolean existsByLicenseNumber(String value);
    boolean existsByLicenseNumberAndIdNot(String value, ProfessionalId id);
}
