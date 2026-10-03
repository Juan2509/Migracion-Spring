package com.migracion.rangel.application.mentalstatusexam.command;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
public record RegisterMentalStatusExamCommand(EncounterId encounterId, String appearance, String behavior, String attitude, String consciousness, String orientation, String attention, String memory, String speech, String mood, String affect, String thoughtProcess, String thoughtContent, String perception, String judgment, String insight, String psychomotorActivity, String observations, ProfessionalId createdBy) {}

