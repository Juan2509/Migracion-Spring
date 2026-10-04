package com.migracion.rangel.infrastructure.medicationroute.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.medicationroute.model.aggregate.MedicationRoute;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
import com.migracion.rangel.domain.medicationroute.port.repository.MedicationRouteRepository;
import com.migracion.rangel.infrastructure.medicationroute.adapters.out.persistence.mappers.MedicationRoutePersistenceMapper;
public class MedicationRouteRepositoryAdapter implements MedicationRouteRepository {
    private final MedicationRouteJpaRepository repository;
    private final MedicationRoutePersistenceMapper mapper;
    public MedicationRouteRepositoryAdapter(MedicationRouteJpaRepository repository, MedicationRoutePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public MedicationRoute save(MedicationRoute aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<MedicationRoute> findById(MedicationRouteId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<MedicationRoute> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(MedicationRoute aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String value) { return repository.existsByCode(value); }
    public boolean existsByCodeAndIdNot(String value, MedicationRouteId id) {
        return repository.existsByCodeAndIdNot(value, id.value());
    }
}

