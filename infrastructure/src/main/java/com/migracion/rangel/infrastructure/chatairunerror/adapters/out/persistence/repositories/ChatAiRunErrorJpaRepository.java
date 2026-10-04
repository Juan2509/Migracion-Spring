package com.migracion.rangel.infrastructure.chatairunerror.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.chatairunerror.adapters.out.persistence.entity.ChatAiRunErrorJpaEntity;
public interface ChatAiRunErrorJpaRepository extends JpaRepository<ChatAiRunErrorJpaEntity, UUID> {
}

