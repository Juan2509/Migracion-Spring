package com.migracion.rangel.domain.sendertype.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.sendertype.model.aggregate.SenderType;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
public interface SenderTypeRepository {
    SenderType save(SenderType aggregate);
    Optional<SenderType> findById(SenderTypeId id);
    List<SenderType> findAll();
    void delete(SenderType aggregate);
}
