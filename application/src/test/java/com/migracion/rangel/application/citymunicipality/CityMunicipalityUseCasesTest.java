package com.migracion.rangel.application.citymunicipality;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.citymunicipality.command.*;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.migracion.rangel.application.citymunicipality.usecase.*;
import com.migracion.rangel.application.stateregion.exception.StateRegionNotFoundApplicationException;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.domain.stateregion.model.aggregate.StateRegion;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;

class CityMunicipalityUseCasesTest {
    @Test
    void crudCanChangeRegionAndReportsMissingCity() {
        var regions = new Regions();
        var country = CountryId.generate();
        var first = regions.save(StateRegion.register("Antioquia", "ANT", "Región", true, country));
        var second = regions.save(StateRegion.register("Cundinamarca", "CUN", "Región", true, country));
        var cities = new Cities();
        var register = new RegisterCityMunicipalityUseCase(cities, regions);
        var get = new GetCityMunicipalityByIdUseCase(cities);
        var list = new ListCityMunicipalityUseCase(cities);
        var update = new UpdateCityMunicipalityUseCase(cities, regions);
        var delete = new DeleteCityMunicipalityUseCase(cities);
        var response = register.execute(new RegisterCityMunicipalityCommand("Medellín", "MED", "Ciudad", true, first.id()));
        var id = new CityMunicipalityId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateCityMunicipalityCommand(id, "Bogotá", "BOG", "Nueva", false, second.id()));
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals(response.id(), changed.id());
        assertEquals("Bogotá", changed.nameCity());
        assertEquals("BOG", changed.codeCiti());
        assertEquals("Nueva", changed.description());
        assertFalse(changed.isActive());
        assertEquals(second.id().value(), changed.regionId());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(CityMunicipalityNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(CityMunicipalityNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(CityMunicipalityNotFoundApplicationException.class, () -> update.execute(
                new UpdateCityMunicipalityCommand(id, "Otra", "OT", "Ciudad", true, first.id())));
    }

    @Test
    void missingRegionCannotCreateOrModifyCity() {
        var regions = new Regions();
        var region = regions.save(StateRegion.register("Antioquia", "ANT", "Región", true, CountryId.generate()));
        var cities = new Cities();
        var register = new RegisterCityMunicipalityUseCase(cities, regions);
        var missing = StateRegionId.generate();
        assertThrows(StateRegionNotFoundApplicationException.class, () -> register.execute(
                new RegisterCityMunicipalityCommand("Medellín", "MED", "Ciudad", true, missing)));
        assertTrue(cities.findAll().isEmpty());
        var response = register.execute(new RegisterCityMunicipalityCommand("Medellín", "MED", "Ciudad", true, region.id()));
        var id = new CityMunicipalityId(response.id());
        assertThrows(StateRegionNotFoundApplicationException.class, () -> new UpdateCityMunicipalityUseCase(cities, regions).execute(
                new UpdateCityMunicipalityCommand(id, "Otra", "OT", "Nueva", false, missing)));
        assertEquals(response, new GetCityMunicipalityByIdUseCase(cities).execute(id));
        assertEquals(1, cities.saves);
    }

    private static class Cities implements CityMunicipalityRepository {
        private final Map<CityMunicipalityId, CityMunicipality> values = new LinkedHashMap<>();
        private int saves;
        public CityMunicipality save(CityMunicipality city) { saves++; values.put(city.id(), city); return city; }
        public Optional<CityMunicipality> findById(CityMunicipalityId id) { return Optional.ofNullable(values.get(id)); }
        public List<CityMunicipality> findAll() { return List.copyOf(values.values()); }
        public void delete(CityMunicipality city) { values.remove(city.id()); }
    }
    private static class Regions implements StateRegionRepository {
        private final Map<StateRegionId, StateRegion> values = new HashMap<>();
        public StateRegion save(StateRegion region) { values.put(region.id(), region); return region; }
        public Optional<StateRegion> findById(StateRegionId id) { return Optional.ofNullable(values.get(id)); }
        public List<StateRegion> findAll() { return List.copyOf(values.values()); }
        public void delete(StateRegion region) { values.remove(region.id()); }
    }
}
