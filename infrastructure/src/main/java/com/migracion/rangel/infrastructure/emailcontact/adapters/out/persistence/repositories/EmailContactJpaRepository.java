package com.migracion.rangel.infrastructure.emailcontact.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.emailcontact.adapters.out.persistence.entity.EmailContactJpaEntity;
public interface EmailContactJpaRepository extends JpaRepository<EmailContactJpaEntity, UUID> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);
}
