package com.migracion.rangel.application.assessmenttype.command;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
public record UpdateAssessmentTypeCommand(AssessmentTypeId id, String code, String name, Boolean active, String description) {}

