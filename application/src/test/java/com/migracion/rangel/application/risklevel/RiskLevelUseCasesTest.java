package com.migracion.rangel.application.risklevel;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.risklevel.command.*;
import com.migracion.rangel.application.risklevel.usecase.*;
import com.migracion.rangel.application.risklevel.exception.*;
import com.migracion.rangel.domain.risklevel.model.aggregate.RiskLevel;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
class RiskLevelUseCasesTest {
    @Test void crudAllowsOwnCodeAndRepeatedNamesAndPreservesCreation() {
        var repo = new Repository(); var register = new RegisterRiskLevelUseCase(repo);
        var first = register.execute(new RegisterRiskLevelCommand("LOW", "Nivel", false, 0));
        var second = register.execute(new RegisterRiskLevelCommand("HIGH", "Nivel", true, -1)); assertEquals(first.name(), second.name());
        var id = new RiskLevelId(first.id()); var get = new GetRiskLevelByIdUseCase(repo); assertEquals(first, get.execute(id));
        assertEquals(2, new ListRiskLevelUseCase(repo).execute().size());
        var changed = new UpdateRiskLevelUseCase(repo).execute(new UpdateRiskLevelCommand(id, "LOW", "Nivel", true, 10));
        assertEquals(first.createdAt(), changed.createdAt()); assertEquals(10, changed.severity());
        var delete = new DeleteRiskLevelUseCase(repo); assertEquals(id, delete.execute(id).id());
        assertThrows(RiskLevelNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(RiskLevelNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(RiskLevelNotFoundApplicationException.class, () -> new UpdateRiskLevelUseCase(repo).execute(new UpdateRiskLevelCommand(id, "LOW", "Nivel", true, 1)));
    }
    @Test void duplicateCodeCannotRegisterOrPartiallyUpdate() {
        var repo = new Repository(); var register = new RegisterRiskLevelUseCase(repo);
        register.execute(new RegisterRiskLevelCommand("LOW", "Nivel", true, 1));
        var original = register.execute(new RegisterRiskLevelCommand("HIGH", "Otro", false, 2)); var id = new RiskLevelId(original.id());
        assertThrows(DuplicateRiskLevelApplicationException.class, () -> register.execute(new RegisterRiskLevelCommand("LOW", "Diferente", false, 0)));
        assertThrows(DuplicateRiskLevelApplicationException.class, () -> new UpdateRiskLevelUseCase(repo).execute(new UpdateRiskLevelCommand(id, "LOW", "Nuevo", true, 100)));
        assertEquals(original, new GetRiskLevelByIdUseCase(repo).execute(id)); assertEquals(2, repo.saves);
    }
    @Test void missingSeverityCannotSaveOrMutate() {
        var repo = new Repository(); var register = new RegisterRiskLevelUseCase(repo);
        assertThrows(NullPointerException.class, () -> register.execute(new RegisterRiskLevelCommand("LOW", "Nivel", true, null)));
        var original = register.execute(new RegisterRiskLevelCommand("LOW", "Nivel", false, 1)); var id = new RiskLevelId(original.id());
        assertThrows(NullPointerException.class, () -> new UpdateRiskLevelUseCase(repo).execute(new UpdateRiskLevelCommand(id, "NEW", "Nuevo", true, null)));
        assertEquals(original, new GetRiskLevelByIdUseCase(repo).execute(id)); assertEquals(1, repo.saves);
    }
    private static class Repository implements RiskLevelRepository {
        final Map<RiskLevelId, RiskLevel> values = new LinkedHashMap<>(); int saves;
        public RiskLevel save(RiskLevel item) { saves++; values.put(item.id(), item); return item; }
        public Optional<RiskLevel> findById(RiskLevelId id) { return Optional.ofNullable(values.get(id)); }
        public List<RiskLevel> findAll() { return List.copyOf(values.values()); }
        public void delete(RiskLevel item) { values.remove(item.id()); }
        public boolean existsByCode(String value) { return values.values().stream().anyMatch(p -> p.code().equals(value)); }
        public boolean existsByCodeAndIdNot(String value, RiskLevelId id) { return values.values().stream().anyMatch(p -> p.code().equals(value) && !p.id().equals(id)); }
    }
}
