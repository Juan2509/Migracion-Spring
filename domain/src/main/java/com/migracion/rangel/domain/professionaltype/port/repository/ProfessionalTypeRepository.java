package com.migracion.rangel.domain.professionaltype.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.professionaltype.model.aggregate.ProfessionalType;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
public interface ProfessionalTypeRepository {
    ProfessionalType save(ProfessionalType aggregate);
    Optional<ProfessionalType> findById(ProfessionalTypeId id);
    List<ProfessionalType> findAll();
    void delete(ProfessionalType aggregate);
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, ProfessionalTypeId id);
}
