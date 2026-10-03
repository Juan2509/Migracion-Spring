package com.migracion.rangel.application.contact.dto;
import java.time.OffsetDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
public record ContactResponse(UUID id, String fullName, String email, String notes,
        UUID cityId, OffsetDateTime createdAt, UUID createdBy,
        OffsetDateTime updatedAt, UUID updatedBy) {
    public static ContactResponse from(Contact contact) {
        return new ContactResponse(contact.id().value(), contact.fullName(), contact.email(),
                contact.notes(), contact.cityId().value(), contact.createdAt(), contact.createdBy().value(),
                contact.updatedAt(), contact.updatedBy() == null ? null : contact.updatedBy().value());
    }
}
