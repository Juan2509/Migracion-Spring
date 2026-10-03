package com.migracion.rangel.application.gender.command;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
public record UpdateGenderCommand(GenderId id, String description) {}
