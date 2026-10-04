package com.migracion.rangel.application.chatairunmetric;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.chatairunmetric.command.*;
import com.migracion.rangel.application.chatairunmetric.usecase.*;
import com.migracion.rangel.application.chatairunmetric.exception.ChatAiRunMetricNotFoundApplicationException;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.migracion.rangel.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import com.migracion.rangel.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import com.migracion.rangel.domain.chatairun.model.aggregate.ChatAiRun;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
class ChatAiRunMetricUseCasesTest {
    private final ChatAiRun run = ChatAiRun.register(ChatConversationId.generate(),ChatMessageId.generate(),AiModelId.generate(),AiRunStatusId.generate());
    private final ChatAiRunRepository runs = (ChatAiRunRepository) Proxy.newProxyInstance(ChatAiRunRepository.class.getClassLoader(),new Class<?>[]{ChatAiRunRepository.class},(proxy,method,args) -> {
        if (method.getName().equals("findById")) { return run.id().equals(args[0]) ? Optional.of(run) : Optional.empty(); }
        throw new UnsupportedOperationException(method.getName());
    });
    @Test
    void crudAllowsRepeatedMetricsAndKeepsCreationDate() {
        var repository = new MemoryRepository();
        var register = new RegisterChatAiRunMetricUseCase(repository,runs);
        var command = new RegisterChatAiRunMetricCommand(run.id(),1,2,9,BigDecimal.ZERO);
        var first = register.execute(command);
        var second = register.execute(command);
        assertNotEquals(first.id(),second.id());
        var id = new ChatAiRunMetricId(first.id());
        var update = new UpdateChatAiRunMetricUseCase(repository,runs);
        var changed = update.execute(new UpdateChatAiRunMetricCommand(id,run.id(),2,3,99,new BigDecimal("0.123456")));
        assertEquals(first.createdAt(),changed.createdAt());
        assertEquals(99,changed.totalTokens());
        assertEquals(changed,new GetChatAiRunMetricByIdUseCase(repository).execute(id));
        assertEquals(2,new ListChatAiRunMetricUseCase(repository).execute().size());
        assertEquals(id,new DeleteChatAiRunMetricUseCase(repository).execute(id).id());
        assertThrows(ChatAiRunMetricNotFoundApplicationException.class, () -> new GetChatAiRunMetricByIdUseCase(repository).execute(id));
        assertThrows(ChatAiRunMetricNotFoundApplicationException.class, () -> new DeleteChatAiRunMetricUseCase(repository).execute(id));
        assertThrows(ChatAiRunMetricNotFoundApplicationException.class, () -> update.execute(new UpdateChatAiRunMetricCommand(id,run.id(),1,2,3,BigDecimal.ZERO)));
    }
    @Test
    void missingRunAndInvalidCostPreventSavingOrChangingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterChatAiRunMetricUseCase(repository,runs);
        assertThrows(ChatAiRunNotFoundApplicationException.class, () -> register.execute(new RegisterChatAiRunMetricCommand(ChatAiRunId.generate(),1,2,3,BigDecimal.ZERO)));
        assertEquals(0,repository.saves);
        var first = register.execute(new RegisterChatAiRunMetricCommand(run.id(),1,2,3,BigDecimal.ZERO));
        var id = new ChatAiRunMetricId(first.id());
        var update = new UpdateChatAiRunMetricUseCase(repository,runs);
        assertThrows(ChatAiRunNotFoundApplicationException.class, () -> update.execute(new UpdateChatAiRunMetricCommand(id,ChatAiRunId.generate(),4,5,6,BigDecimal.ONE)));
        assertThrows(IllegalArgumentException.class, () -> update.execute(new UpdateChatAiRunMetricCommand(id,run.id(),4,5,6,new BigDecimal("10000"))));
        assertEquals(first,new GetChatAiRunMetricByIdUseCase(repository).execute(id));
        assertEquals(1,repository.saves);
    }
    private static class MemoryRepository implements ChatAiRunMetricRepository {
        private final Map<ChatAiRunMetricId,ChatAiRunMetric> values = new LinkedHashMap<>();
        private int saves;
        public ChatAiRunMetric save(ChatAiRunMetric value) { saves++; values.put(value.id(),value); return value; }
        public Optional<ChatAiRunMetric> findById(ChatAiRunMetricId id) { return Optional.ofNullable(values.get(id)); }
        public List<ChatAiRunMetric> findAll() { return List.copyOf(values.values()); }
        public void delete(ChatAiRunMetric value) { values.remove(value.id()); }
    }
}
