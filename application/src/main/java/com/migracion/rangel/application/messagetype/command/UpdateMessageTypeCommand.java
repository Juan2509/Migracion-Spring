package com.migracion.rangel.application.messagetype.command;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
public record UpdateMessageTypeCommand(MessageTypeId id, String nameType) {}
