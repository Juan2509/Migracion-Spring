package com.migracion.rangel.infrastructure.contact.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.contact.adapters.out.persistence.entity.ContactJpaEntity;
public interface ContactJpaRepository extends JpaRepository<ContactJpaEntity, UUID> {}
