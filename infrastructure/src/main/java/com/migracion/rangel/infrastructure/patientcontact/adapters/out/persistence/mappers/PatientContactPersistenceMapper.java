package com.migracion.rangel.infrastructure.patientcontact.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.domain.patientcontact.model.aggregate.PatientContact;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
import com.migracion.rangel.infrastructure.patientcontact.adapters.out.persistence.entity.PatientContactJpaEntity;
public class PatientContactPersistenceMapper {
    public PatientContactJpaEntity toJpa(PatientContact aggregate) {
        if (aggregate == null) return null;
        var entity = new PatientContactJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setContactId(aggregate.contactId().value());
        entity.setPatientId(aggregate.patientId().value());
        entity.setIsPrimaryContact(aggregate.isPrimaryContact());
        entity.setIsEmergencyContact(aggregate.isEmergencyContact());
        entity.setRelationshipTypeId(aggregate.relationshipTypeId().value());

        return entity;
    }
    public PatientContact toDomain(PatientContactJpaEntity entity) {
        if (entity == null) return null;
        return PatientContact.restore(new PatientContactId(entity.getId()), new ContactId(entity.getContactId()), new PatientId(entity.getPatientId()), entity.getIsPrimaryContact(), entity.getIsEmergencyContact(), new RelationshipTypeId(entity.getRelationshipTypeId()));
    }
}
