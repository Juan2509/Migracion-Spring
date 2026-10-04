package com.migracion.rangel.application.aimodel;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.aimodel.command.*;
import com.migracion.rangel.application.aimodel.usecase.*;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import com.migracion.rangel.domain.aimodel.model.aggregate.AiModel;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
class AiModelUseCasesTest {
    @Test
    void crudAllowsRepeatedValuesAndTextualProviderAndKeepsAudit() {
        var repository = new MemoryRepository();
        var register = new RegisterAiModelUseCase(repository);
        var command = new RegisterAiModelCommand("proveedor textual", "Modelo", "key", BigDecimal.ZERO, BigDecimal.ONE, 1, 2, true);
        var first = register.execute(command);
        var second = register.execute(command);
        assertNotEquals(first.id(), second.id());
        assertEquals(2, new ListAiModelUseCase(repository).execute().size());
        var id = new AiModelId(first.id());
        var changed = new UpdateAiModelUseCase(repository).execute(new UpdateAiModelCommand(id, "otro texto", "Nuevo", "key", BigDecimal.ONE, BigDecimal.ZERO, 2, 3, false));
        assertEquals(first.createdAt(), changed.createdAt());
        assertEquals("otro texto", changed.providerModelId());
        assertEquals(changed, new GetAiModelByIdUseCase(repository).execute(id));
        assertEquals(id, new DeleteAiModelUseCase(repository).execute(id).id());
        assertThrows(AiModelNotFoundApplicationException.class, () -> new GetAiModelByIdUseCase(repository).execute(id));
        assertThrows(AiModelNotFoundApplicationException.class, () -> new DeleteAiModelUseCase(repository).execute(id));
        assertThrows(AiModelNotFoundApplicationException.class, () -> new UpdateAiModelUseCase(repository).execute(new UpdateAiModelCommand(id, "p", "n", "k", BigDecimal.ZERO, BigDecimal.ZERO, 1, 1, true)));
    }
    @Test
    void invalidPriceDoesNotSaveOrModifyRecord() {
        var repository = new MemoryRepository();
        var response = new RegisterAiModelUseCase(repository).execute(new RegisterAiModelCommand("p", "n", "k", BigDecimal.ZERO, BigDecimal.ZERO, 1, 1, true));
        var id = new AiModelId(response.id());
        assertThrows(IllegalArgumentException.class, () -> new UpdateAiModelUseCase(repository).execute(
                new UpdateAiModelCommand(id, "otro", "otro", "otro", BigDecimal.ZERO, new BigDecimal("10000"), 2, 2, false)));
        assertEquals(response, new GetAiModelByIdUseCase(repository).execute(id));
        assertEquals(1, repository.saves);
    }
    private static class MemoryRepository implements AiModelRepository {
        private final Map<AiModelId,AiModel> values = new LinkedHashMap<>();
        private int saves;
        public AiModel save(AiModel value) { saves++; values.put(value.id(), value); return value; }
        public Optional<AiModel> findById(AiModelId id) { return Optional.ofNullable(values.get(id)); }
        public List<AiModel> findAll() { return List.copyOf(values.values()); }
        public void delete(AiModel value) { values.remove(value.id()); }
    }
}
