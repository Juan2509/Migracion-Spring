package com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.repositories;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.entity.ChatAiRunMetricJpaEntity;
public interface ChatAiRunMetricJpaRepository extends JpaRepository<ChatAiRunMetricJpaEntity, UUID> {
}

