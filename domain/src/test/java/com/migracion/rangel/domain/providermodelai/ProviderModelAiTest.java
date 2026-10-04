package com.migracion.rangel.domain.providermodelai;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.providermodelai.model.aggregate.ProviderModelAi;
import com.migracion.rangel.domain.providermodelai.event.ProviderModelAiRegisteredEvent;
import com.migracion.rangel.domain.providermodelai.event.ProviderModelAiUpdatedEvent;

class ProviderModelAiTest {
    @Test
    void providerNameHasLimitButLegalNameAndWebsiteHaveNoArtificialLimit() {
        var aggregate = ProviderModelAi.register("😀".repeat(100), "r".repeat(1000), true, "s".repeat(2000));
        assertEquals("😀".repeat(100), aggregate.nameProviderAi());
        assertEquals("r".repeat(1000), aggregate.razonSocial());
        assertEquals("s".repeat(2000), aggregate.sitioWeb());
        assertThrows(IllegalArgumentException.class,
                () -> ProviderModelAi.register("😀".repeat(101), "Razón", true, "Sitio"));
    }

    @Test
    void missingDescriptionDoesNotPartiallyUpdateAggregate() {
        var aggregate = ProviderModelAi.register("ORIGINAL", "Nombre", true, "Descripción");
        var updatedAt = aggregate.updatedAt();
        assertThrows(NullPointerException.class,
                () -> aggregate.update("CHANGED", "Otro nombre", false, null));
        assertEquals("ORIGINAL", aggregate.nameProviderAi());
        assertEquals("Nombre", aggregate.razonSocial());
        assertEquals(true, aggregate.isActive());
        assertEquals("Descripción", aggregate.sitioWeb());
        assertEquals(updatedAt, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class,
                () -> ProviderModelAi.register("CODE", "Nombre", true, null));
    }

    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = ProviderModelAi.register("CC", "Cédula", true, "Descripción");
        assertInstanceOf(ProviderModelAiRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = ProviderModelAi.restore(original.id(), "CC", "Cédula", true, "Descripción", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro nameProviderAi", "Otro razonSocial", false, "Otra descripción");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(ProviderModelAiUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro nameProviderAi", restored.nameProviderAi());
        assertEquals("Otro razonSocial", restored.razonSocial());
        assertEquals(false, restored.isActive());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = ProviderModelAi.register("CC", "Cédula", true, "Descripción");
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(101), "Cédula", true, "Descripción"));
        assertEquals("CC", aggregate.nameProviderAi());
        assertEquals("Cédula", aggregate.razonSocial());
        assertEquals(true, aggregate.isActive());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> ProviderModelAi.register(null, "Cédula", true, "Descripción"));
    }
}
