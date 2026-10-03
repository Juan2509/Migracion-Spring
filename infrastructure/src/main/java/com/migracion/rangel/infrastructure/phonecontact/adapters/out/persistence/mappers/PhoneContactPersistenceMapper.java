package com.migracion.rangel.infrastructure.phonecontact.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.phonecontact.model.aggregate.PhoneContact;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.infrastructure.phonecontact.adapters.out.persistence.entity.PhoneContactJpaEntity;
public class PhoneContactPersistenceMapper {
    public PhoneContactJpaEntity toJpa(PhoneContact aggregate) {
        if (aggregate == null) { return null; }
        var entity = new PhoneContactJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setContactId(aggregate.contactId().value());
        entity.setPhone(aggregate.phone());
        entity.setNotes(aggregate.notes());
        return entity;
    }
    public PhoneContact toDomain(PhoneContactJpaEntity entity) {
        if (entity == null) { return null; }
        return PhoneContact.restore(new PhoneContactId(entity.getId()), new ContactId(entity.getContactId()),
                entity.getPhone(), entity.getNotes());
    }
}
