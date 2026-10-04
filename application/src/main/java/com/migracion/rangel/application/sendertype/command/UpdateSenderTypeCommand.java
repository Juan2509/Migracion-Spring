package com.migracion.rangel.application.sendertype.command;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
public record UpdateSenderTypeCommand(SenderTypeId id, String nameType) {}
