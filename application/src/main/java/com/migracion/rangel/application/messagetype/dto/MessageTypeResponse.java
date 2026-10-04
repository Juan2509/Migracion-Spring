package com.migracion.rangel.application.messagetype.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.messagetype.model.aggregate.MessageType;
public record MessageTypeResponse(UUID id, String nameType, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static MessageTypeResponse from(MessageType aggregate) {
        return new MessageTypeResponse(aggregate.id().value(), aggregate.nameType(), aggregate.createdAt(), aggregate.updatedAt());
    }
}
