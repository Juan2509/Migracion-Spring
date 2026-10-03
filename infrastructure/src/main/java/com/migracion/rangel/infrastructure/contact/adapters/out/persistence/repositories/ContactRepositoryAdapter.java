package com.migracion.rangel.infrastructure.contact.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.infrastructure.contact.adapters.out.persistence.mappers.ContactPersistenceMapper;

public class ContactRepositoryAdapter implements ContactRepository {
    private final ContactJpaRepository repository;
    private final ContactPersistenceMapper mapper;
    public ContactRepositoryAdapter(ContactJpaRepository repository, ContactPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public Contact save(Contact contact) { return mapper.toDomain(repository.save(mapper.toJpa(contact))); }
    public Optional<Contact> findById(ContactId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<Contact> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(Contact contact) { repository.deleteById(contact.id().value()); }
}
