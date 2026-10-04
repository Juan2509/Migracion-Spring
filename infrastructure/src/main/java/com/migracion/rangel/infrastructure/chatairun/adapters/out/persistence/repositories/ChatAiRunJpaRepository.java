package com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.entity.ChatAiRunJpaEntity;
public interface ChatAiRunJpaRepository extends JpaRepository<ChatAiRunJpaEntity, UUID> {
}

