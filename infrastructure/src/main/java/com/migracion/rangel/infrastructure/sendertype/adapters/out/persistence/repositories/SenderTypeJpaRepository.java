package com.migracion.rangel.infrastructure.sendertype.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.sendertype.adapters.out.persistence.entity.SenderTypeJpaEntity;
public interface SenderTypeJpaRepository extends JpaRepository<SenderTypeJpaEntity, UUID> {
}
