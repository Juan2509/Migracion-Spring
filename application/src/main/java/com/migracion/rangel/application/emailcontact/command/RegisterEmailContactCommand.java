package com.migracion.rangel.application.emailcontact.command;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;

public record RegisterEmailContactCommand(ContactId contactId, String email, String notes) {}
