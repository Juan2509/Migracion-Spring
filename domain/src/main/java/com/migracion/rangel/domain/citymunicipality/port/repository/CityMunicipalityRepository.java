package com.migracion.rangel.domain.citymunicipality.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
public interface CityMunicipalityRepository {
    CityMunicipality save(CityMunicipality city);
    Optional<CityMunicipality> findById(CityMunicipalityId id);
    List<CityMunicipality> findAll();
    void delete(CityMunicipality city);
}
