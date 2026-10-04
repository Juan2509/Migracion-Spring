package com.migracion.rangel.infrastructure.chatmessage.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.LocalDateTime;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import com.migracion.rangel.application.chatmessage.dto.ChatMessageResponse;
public record ChatMessageRestResponse(UUID id, UUID conversationId, UUID messageTypeId, UUID participantId, JsonNode content, JsonNode metadata, LocalDateTime createdAt) {
    private static final JsonMapper JSON = JsonMapper.builder().build();
    public static ChatMessageRestResponse from(ChatMessageResponse response) {
        return new ChatMessageRestResponse(response.id(), response.conversationId(), response.messageTypeId(), response.participantId(), JSON.readTree(response.content()), JSON.readTree(response.metadata()), response.createdAt());
    }
}
