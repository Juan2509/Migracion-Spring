package com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.entity.ChatEscalationAssignmentJpaEntity;
public interface ChatEscalationAssignmentJpaRepository extends JpaRepository<ChatEscalationAssignmentJpaEntity, UUID> {
}

