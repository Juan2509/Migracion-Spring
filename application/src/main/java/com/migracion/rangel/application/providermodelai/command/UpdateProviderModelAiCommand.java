package com.migracion.rangel.application.providermodelai.command;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
public record UpdateProviderModelAiCommand(ProviderModelAiId id, String nameProviderAi, String razonSocial, Boolean isActive, String sitioWeb) {}

