package com.migracion.rangel.application.stateregion;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.stateregion.command.*;
import com.migracion.rangel.application.stateregion.exception.StateRegionNotFoundApplicationException;
import com.migracion.rangel.application.stateregion.usecase.*;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
import com.migracion.rangel.domain.country.model.aggregate.Country;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.domain.stateregion.model.aggregate.StateRegion;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;

class StateRegionUseCasesTest {
    @Test
    void crudSupportsChangingCountryAndReportsMissingRegion() {
        var countries = new Countries();
        var first = countries.save(Country.register("Colombia", "CO", "País", true, "+57"));
        var second = countries.save(Country.register("Ecuador", "EC", "País", true, "+593"));
        var regions = new Regions();
        var register = new RegisterStateRegionUseCase(regions, countries);
        var get = new GetStateRegionByIdUseCase(regions);
        var list = new ListStateRegionUseCase(regions);
        var update = new UpdateStateRegionUseCase(regions, countries);
        var delete = new DeleteStateRegionUseCase(regions);
        var response = register.execute(new RegisterStateRegionCommand("Antioquia", "ANT", "Región", true, first.id()));
        var id = new StateRegionId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateStateRegionCommand(id, "Pichincha", "PIC", "Nueva", false, second.id()));
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals(response.id(), changed.id());
        assertEquals("Pichincha", changed.nameRegion());
        assertEquals("PIC", changed.codeRegion());
        assertEquals("Nueva", changed.description());
        assertFalse(changed.isActive());
        assertEquals(second.id().value(), changed.countryId());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(StateRegionNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(StateRegionNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(StateRegionNotFoundApplicationException.class, () -> update.execute(
                new UpdateStateRegionCommand(id, "Otra", "OT", "Región", true, first.id())));
    }

    @Test
    void missingCountryCannotCreateOrModifyRegion() {
        var countries = new Countries();
        var country = countries.save(Country.register("Colombia", "CO", "País", true, "+57"));
        var regions = new Regions();
        var register = new RegisterStateRegionUseCase(regions, countries);
        var missing = CountryId.generate();
        assertThrows(CountryNotFoundApplicationException.class, () -> register.execute(
                new RegisterStateRegionCommand("Antioquia", "ANT", "Región", true, missing)));
        assertTrue(regions.findAll().isEmpty());
        var response = register.execute(new RegisterStateRegionCommand("Antioquia", "ANT", "Región", true, country.id()));
        var id = new StateRegionId(response.id());
        var update = new UpdateStateRegionUseCase(regions, countries);
        assertThrows(CountryNotFoundApplicationException.class, () -> update.execute(
                new UpdateStateRegionCommand(id, "Otra", "OT", "Nueva", false, missing)));
        assertEquals(response, new GetStateRegionByIdUseCase(regions).execute(id));
        assertEquals(1, regions.saves);
    }

    private static class Regions implements StateRegionRepository {
        private final Map<StateRegionId, StateRegion> values = new LinkedHashMap<>();
        private int saves;
        public StateRegion save(StateRegion region) { saves++; values.put(region.id(), region); return region; }
        public Optional<StateRegion> findById(StateRegionId id) { return Optional.ofNullable(values.get(id)); }
        public List<StateRegion> findAll() { return List.copyOf(values.values()); }
        public void delete(StateRegion region) { values.remove(region.id()); }
    }
    private static class Countries implements CountryRepository {
        private final Map<CountryId, Country> values = new HashMap<>();
        public Country save(Country country) { values.put(country.id(), country); return country; }
        public Optional<Country> findById(CountryId id) { return Optional.ofNullable(values.get(id)); }
        public List<Country> findAll() { return List.copyOf(values.values()); }
        public void delete(Country country) { values.remove(country.id()); }
    }
}
