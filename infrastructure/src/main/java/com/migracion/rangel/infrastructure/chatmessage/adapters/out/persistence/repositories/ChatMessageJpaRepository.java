package com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.chatmessage.adapters.out.persistence.entity.ChatMessageJpaEntity;
public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageJpaEntity, UUID> {
}

