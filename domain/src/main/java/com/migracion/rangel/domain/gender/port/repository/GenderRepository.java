package com.migracion.rangel.domain.gender.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.gender.model.aggregate.Gender;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
public interface GenderRepository {
    Gender save(Gender aggregate);
    Optional<Gender> findById(GenderId id);
    List<Gender> findAll();
    void delete(Gender aggregate);
    boolean existsByDescription(String value);
    boolean existsByDescriptionAndIdNot(String value, GenderId id);
}
