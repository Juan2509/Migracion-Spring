package com.migracion.rangel.infrastructure.country;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.country.model.aggregate.Country;
import com.migracion.rangel.infrastructure.country.adapters.out.persistence.mappers.CountryPersistenceMapper;

class CountryPersistenceMapperTest {
    @Test
    void roundTripPreservesEverySqlFieldWithoutGeneratingEvents() {
        var country = Country.register("Colombia", "CO", "País", false, "+57");
        var mapper = new CountryPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(country));
        assertEquals(country.id(), restored.id());
        assertEquals(country.nameCountry(), restored.nameCountry());
        assertEquals(country.codeCountry(), restored.codeCountry());
        assertEquals(country.description(), restored.description());
        assertEquals(country.isActive(), restored.isActive());
        assertEquals(country.telephonePrefix(), restored.telephonePrefix());
        assertEquals(country.createdAt(), restored.createdAt());
        assertEquals(country.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
