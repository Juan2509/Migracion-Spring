package com.migracion.rangel.application.treatmentgoal;
import java.util.*;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.treatmentgoal.model.aggregate.TreatmentGoal;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.application.treatmentgoal.command.*;
import com.migracion.rangel.application.treatmentgoal.usecase.*;
import com.migracion.rangel.application.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
import com.migracion.rangel.application.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.migracion.rangel.domain.treatmentgoal.port.repository.TreatmentGoalRepository;
import com.migracion.rangel.domain.treatmentplan.model.aggregate.TreatmentPlan;
import com.migracion.rangel.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import com.migracion.rangel.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
class TreatmentGoalUseCasesTest {
    @Test void crudChangesBothReferencesAndDatesPreservingCreation() {
        var f = new Fixture(); var original = f.register.execute(f.command(f.plan.id(), f.status.id())); var id = new TreatmentGoalId(original.id());
        var get = new GetTreatmentGoalByIdUseCase(f.repository); assertEquals(original, get.execute(id)); assertEquals(List.of(original), new ListTreatmentGoalUseCase(f.repository).execute());
        var changed = f.update.execute(new UpdateTreatmentGoalCommand(id, f.otherPlan.id(), "Otra", f.target.plusDays(1), f.completed.plusDays(1), "Otras", f.otherStatus.id()));
        assertEquals(original.createdAt(), changed.createdAt()); assertEquals(f.otherPlan.id().value(), changed.treatmentPlanId()); assertEquals(f.otherStatus.id().value(), changed.treatmentGoalId());
        assertEquals("Otra", changed.description()); assertEquals("Otras", changed.notes()); assertEquals(f.target.plusDays(1), changed.targetDate()); assertEquals(f.completed.plusDays(1), changed.completedAt());
        var delete = new DeleteTreatmentGoalUseCase(f.repository); assertEquals(id, delete.execute(id).id()); assertTrue(f.repository.findAll().isEmpty());
        assertThrows(TreatmentGoalNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(TreatmentGoalNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(TreatmentGoalNotFoundApplicationException.class, () -> f.update.execute(new UpdateTreatmentGoalCommand(id, f.plan.id(), "Otra", f.target, f.completed, "Otras", f.status.id())));
    }
    @Test void eachMissingParentPreventsRegistrationAndPartialUpdate() {
        var exceptions = List.of(TreatmentPlanNotFoundApplicationException.class, TreatmentGoalStatusNotFoundApplicationException.class);
        for (int i = 0; i < 2; i++) {
            var f = new Fixture(); var original = f.register.execute(f.command(f.plan.id(), f.status.id())); var id = new TreatmentGoalId(original.id());
            var plan = i == 0 ? TreatmentPlanId.generate() : f.plan.id(); var status = i == 1 ? TreatmentGoalStatusId.generate() : f.status.id();
            assertThrows(exceptions.get(i), () -> f.register.execute(f.command(plan, status)));
            assertThrows(exceptions.get(i), () -> f.update.execute(new UpdateTreatmentGoalCommand(id, plan, "Otra", f.target, f.completed, "Otras", status)));
            assertEquals(original, new GetTreatmentGoalByIdUseCase(f.repository).execute(id)); assertEquals(1, f.repository.saves);
        }
    }
    @Test void repeatedGoalsAndInactiveStatusAreAllowedButCompletionAndNotesCannotBeNull() {
        var f = new Fixture(); var first = f.register.execute(f.command(f.plan.id(), f.status.id())); var other = f.register.execute(f.command(f.plan.id(), f.status.id()));
        assertNotEquals(first.id(), other.id()); assertFalse(f.status.active()); var id = new TreatmentGoalId(first.id());
        assertThrows(NullPointerException.class, () -> f.register.execute(new RegisterTreatmentGoalCommand(f.plan.id(), "Otra", f.target, null, "Otras", f.status.id())));
        assertThrows(NullPointerException.class, () -> f.update.execute(new UpdateTreatmentGoalCommand(id, f.otherPlan.id(), "Otra", f.target, f.completed, null, f.status.id())));
        assertEquals(first, new GetTreatmentGoalByIdUseCase(f.repository).execute(id)); assertEquals(2, f.repository.saves);
    }
    private static <T> T parent(Class<T> type, Map<?, ?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) return Optional.ofNullable(values.get(args[0]));
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static TreatmentPlan plan(String title) { return TreatmentPlan.register(EncounterId.generate(), title, "Descripción", LocalDate.of(2026, 10, 3), LocalDate.of(2026, 10, 4), TreatmentStatusId.generate(), ProfessionalId.generate()); }
    private static class Fixture {
        final TreatmentPlan plan = plan("Uno"), otherPlan = plan("Dos");
        final TreatmentGoalStatus status = TreatmentGoalStatus.register("ONE", "Uno", false), otherStatus = TreatmentGoalStatus.register("TWO", "Dos", true);
        final LocalDate target = LocalDate.of(2026, 10, 3);
        final OffsetDateTime completed = OffsetDateTime.parse("2026-10-03T10:30:00-05:00");
        final TreatmentPlanRepository plans = parent(TreatmentPlanRepository.class, Map.of(plan.id(), plan, otherPlan.id(), otherPlan));
        final TreatmentGoalStatusRepository statuses = parent(TreatmentGoalStatusRepository.class, Map.of(status.id(), status, otherStatus.id(), otherStatus));
        final Repository repository = new Repository();
        final RegisterTreatmentGoalUseCase register = new RegisterTreatmentGoalUseCase(repository, plans, statuses);
        final UpdateTreatmentGoalUseCase update = new UpdateTreatmentGoalUseCase(repository, plans, statuses);
        RegisterTreatmentGoalCommand command(TreatmentPlanId plan, TreatmentGoalStatusId status) { return new RegisterTreatmentGoalCommand(plan, "Descripción", target, completed, "Notas", status); }
    }
    private static class Repository implements TreatmentGoalRepository {
        final Map<TreatmentGoalId, TreatmentGoal> values = new LinkedHashMap<>(); int saves;
        public TreatmentGoal save(TreatmentGoal item) { saves++; values.put(item.id(), item); return item; }
        public Optional<TreatmentGoal> findById(TreatmentGoalId id) { return Optional.ofNullable(values.get(id)); }
        public List<TreatmentGoal> findAll() { return List.copyOf(values.values()); }
        public void delete(TreatmentGoal item) { values.remove(item.id()); }
    }
}
