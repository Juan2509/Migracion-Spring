package com.migracion.rangel.application.country;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.country.command.RegisterCountryCommand;
import com.migracion.rangel.application.country.command.UpdateCountryCommand;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
import com.migracion.rangel.application.country.usecase.*;
import com.migracion.rangel.domain.country.model.aggregate.Country;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;

class CountryUseCasesTest {
    @Test
    void completeCrudPreservesFieldsAndRejectsMissingCountry() {
        var repository = new MemoryRepository();
        var register = new RegisterCountryUseCase(repository);
        var get = new GetCountryByIdUseCase(repository);
        var list = new ListCountryUseCase(repository);
        var update = new UpdateCountryUseCase(repository);
        var delete = new DeleteCountryUseCase(repository);

        var response = register.execute(new RegisterCountryCommand("Colombia", "CO", "País", true, "+57"));
        var id = new CountryId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());

        var changed = update.execute(new UpdateCountryCommand(id, "Ecuador", "EC", "Nuevo", false, "+593"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Ecuador", changed.nameCountry());
        assertEquals("EC", changed.codeCountry());
        assertEquals("Nuevo", changed.description());
        assertEquals("+593", changed.telephonePrefix());
        assertFalse(changed.isActive());
        assertEquals(changed, get.execute(id));

        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(CountryNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(CountryNotFoundApplicationException.class, () -> update.execute(
                new UpdateCountryCommand(id, "Colombia", "CO", "País", true, "+57")));
        assertThrows(CountryNotFoundApplicationException.class, () -> delete.execute(id));
    }

    private static class MemoryRepository implements CountryRepository {
        private final Map<CountryId, Country> countries = new LinkedHashMap<>();
        public Country save(Country country) { countries.put(country.id(), country); return country; }
        public Optional<Country> findById(CountryId id) { return Optional.ofNullable(countries.get(id)); }
        public List<Country> findAll() { return List.copyOf(countries.values()); }
        public void delete(Country country) { countries.remove(country.id()); }
    }
}
