package com.migracion.rangel.domain.consenttype.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.consenttype.model.aggregate.ConsentType;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
public interface ConsentTypeRepository {
    ConsentType save(ConsentType aggregate);
    Optional<ConsentType> findById(ConsentTypeId id);
    List<ConsentType> findAll();
    void delete(ConsentType aggregate);
}

