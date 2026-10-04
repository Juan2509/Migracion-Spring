package com.migracion.rangel.application.treatmentplan.command;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
public record RegisterTreatmentPlanCommand(EncounterId encounterId, String title, String description, LocalDate startDate, LocalDate endDate, TreatmentStatusId treatmentStatusId, ProfessionalId professionalId) {}



