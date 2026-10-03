package com.migracion.rangel.domain.emailcontact.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.emailcontact.model.aggregate.EmailContact;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
public interface EmailContactRepository {
    EmailContact save(EmailContact aggregate);
    Optional<EmailContact> findById(EmailContactId id);
    List<EmailContact> findAll();
    void delete(EmailContact aggregate);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, EmailContactId id);
}
