package com.migracion.rangel.infrastructure.phonecontact.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.phonecontact.model.aggregate.PhoneContact;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
import com.migracion.rangel.domain.phonecontact.port.repository.PhoneContactRepository;
import com.migracion.rangel.infrastructure.phonecontact.adapters.out.persistence.mappers.PhoneContactPersistenceMapper;
public class PhoneContactRepositoryAdapter implements PhoneContactRepository {
    private final PhoneContactJpaRepository repository;
    private final PhoneContactPersistenceMapper mapper;
    public PhoneContactRepositoryAdapter(PhoneContactJpaRepository repository, PhoneContactPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public PhoneContact save(PhoneContact aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<PhoneContact> findById(PhoneContactId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<PhoneContact> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(PhoneContact aggregate) { repository.deleteById(aggregate.id().value()); }

}
