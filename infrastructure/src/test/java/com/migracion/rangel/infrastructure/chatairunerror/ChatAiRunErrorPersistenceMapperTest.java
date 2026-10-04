package com.migracion.rangel.infrastructure.chatairunerror;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatairunerror.model.aggregate.ChatAiRunError;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.application.chatairunerror.dto.ChatAiRunErrorResponse;
import com.migracion.rangel.infrastructure.chatairunerror.adapters.out.persistence.mappers.ChatAiRunErrorPersistenceMapper;
class ChatAiRunErrorPersistenceMapperTest {
    @Test
    void roundTripPreservesAllFieldsWithoutEvents() {
        var original = ChatAiRunError.register(ChatAiRunId.generate(),"Error detallado","E1","P1");
        var mapper = new ChatAiRunErrorPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ChatAiRunErrorResponse.from(original),ChatAiRunErrorResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
}
