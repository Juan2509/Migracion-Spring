package com.migracion.rangel.infrastructure.contact.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.infrastructure.contact.adapters.out.persistence.entity.ContactJpaEntity;

public class ContactPersistenceMapper {
    public ContactJpaEntity toJpa(Contact contact) {
        if (contact == null) { return null; }
        var entity = new ContactJpaEntity();
        entity.setId(contact.id().value());
        entity.setFullName(contact.fullName());
        entity.setEmail(contact.email());
        entity.setNotes(contact.notes());
        entity.setCityId(contact.cityId().value());
        entity.setCreatedAt(contact.createdAt());
        entity.setCreatedBy(contact.createdBy().value());
        entity.setUpdatedAt(contact.updatedAt());
        entity.setUpdatedBy(contact.updatedBy() == null ? null : contact.updatedBy().value());
        return entity;
    }
    public Contact toDomain(ContactJpaEntity entity) {
        if (entity == null) { return null; }
        return Contact.restore(new ContactId(entity.getId()), entity.getFullName(), entity.getEmail(),
                entity.getNotes(), new CityMunicipalityId(entity.getCityId()), entity.getCreatedAt(),
                new ProfessionalId(entity.getCreatedBy()), entity.getUpdatedAt(),
                entity.getUpdatedBy() == null ? null : new ProfessionalId(entity.getUpdatedBy()));
    }
}
