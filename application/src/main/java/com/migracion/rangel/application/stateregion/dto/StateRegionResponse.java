package com.migracion.rangel.application.stateregion.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.stateregion.model.aggregate.StateRegion;
public record StateRegionResponse(UUID id, String nameRegion, String codeRegion,
        String description, Boolean isActive, UUID countryId,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static StateRegionResponse from(StateRegion region) {
        return new StateRegionResponse(region.id().value(), region.nameRegion(), region.codeRegion(),
                region.description(), region.isActive(), region.countryId().value(),
                region.createdAt(), region.updatedAt());
    }
}
