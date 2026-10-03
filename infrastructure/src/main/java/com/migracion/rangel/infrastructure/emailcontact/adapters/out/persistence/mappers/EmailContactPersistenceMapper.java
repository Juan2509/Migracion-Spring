package com.migracion.rangel.infrastructure.emailcontact.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.emailcontact.model.aggregate.EmailContact;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.infrastructure.emailcontact.adapters.out.persistence.entity.EmailContactJpaEntity;
public class EmailContactPersistenceMapper {
    public EmailContactJpaEntity toJpa(EmailContact aggregate) {
        if (aggregate == null) { return null; }
        var entity = new EmailContactJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setContactId(aggregate.contactId().value());
        entity.setEmail(aggregate.email());
        entity.setNotes(aggregate.notes());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public EmailContact toDomain(EmailContactJpaEntity entity) {
        if (entity == null) { return null; }
        return EmailContact.restore(new EmailContactId(entity.getId()), new ContactId(entity.getContactId()),
                entity.getEmail(), entity.getNotes(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
