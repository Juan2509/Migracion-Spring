package com.migracion.rangel.application.study.command;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
public record UpdateStudyCommand(StudyId id, String name) {}
