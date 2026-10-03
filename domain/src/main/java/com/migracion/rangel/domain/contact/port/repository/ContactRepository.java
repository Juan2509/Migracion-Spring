package com.migracion.rangel.domain.contact.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
public interface ContactRepository {
    Contact save(Contact contact);
    Optional<Contact> findById(ContactId id);
    List<Contact> findAll();
    void delete(Contact contact);
}
