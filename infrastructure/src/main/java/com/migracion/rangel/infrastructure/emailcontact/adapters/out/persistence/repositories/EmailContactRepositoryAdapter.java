package com.migracion.rangel.infrastructure.emailcontact.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.emailcontact.model.aggregate.EmailContact;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
import com.migracion.rangel.domain.emailcontact.port.repository.EmailContactRepository;
import com.migracion.rangel.infrastructure.emailcontact.adapters.out.persistence.mappers.EmailContactPersistenceMapper;
public class EmailContactRepositoryAdapter implements EmailContactRepository {
    private final EmailContactJpaRepository repository;
    private final EmailContactPersistenceMapper mapper;
    public EmailContactRepositoryAdapter(EmailContactJpaRepository repository, EmailContactPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public EmailContact save(EmailContact aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<EmailContact> findById(EmailContactId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<EmailContact> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(EmailContact aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByEmail(String email) { return repository.existsByEmail(email); }
    public boolean existsByEmailAndIdNot(String email, EmailContactId id) {
        return repository.existsByEmailAndIdNot(email, id.value());
    }
}
