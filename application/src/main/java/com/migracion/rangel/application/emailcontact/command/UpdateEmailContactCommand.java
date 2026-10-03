package com.migracion.rangel.application.emailcontact.command;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
public record UpdateEmailContactCommand(EmailContactId id, ContactId contactId, String email, String notes) {}
