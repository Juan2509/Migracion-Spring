package com.migracion.rangel.application.phonecontact.command;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
public record UpdatePhoneContactCommand(PhoneContactId id, ContactId contactId, String phone, String notes) {}
