package com.migracion.rangel.infrastructure.country.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.country.adapters.out.persistence.entity.CountryJpaEntity;
public interface CountryJpaRepository extends JpaRepository<CountryJpaEntity, UUID> {}
