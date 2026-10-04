package com.migracion.rangel.application.providermodelai.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.providermodelai.model.aggregate.ProviderModelAi;
public record ProviderModelAiResponse(UUID id, String nameProviderAi, String razonSocial, Boolean isActive, String sitioWeb, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static ProviderModelAiResponse from(ProviderModelAi aggregate) {
        return new ProviderModelAiResponse(aggregate.id().value(), aggregate.nameProviderAi(), aggregate.razonSocial(), aggregate.isActive(), aggregate.sitioWeb(), aggregate.createdAt(), aggregate.updatedAt());
    }
}

