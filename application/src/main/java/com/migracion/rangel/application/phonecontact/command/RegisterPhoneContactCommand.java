package com.migracion.rangel.application.phonecontact.command;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;

public record RegisterPhoneContactCommand(ContactId contactId, String phone, String notes) {}
