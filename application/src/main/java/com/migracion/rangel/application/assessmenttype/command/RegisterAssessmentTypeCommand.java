package com.migracion.rangel.application.assessmenttype.command;

public record RegisterAssessmentTypeCommand(String code, String name, Boolean active, String description) {}

