package com.migracion.rangel.application.documenttype.command;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
public record UpdateDocumentTypeCommand(DocumentTypeId id, String code, String name, Boolean active) {}
