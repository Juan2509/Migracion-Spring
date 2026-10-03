package com.migracion.rangel.application.patient.command;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
public record UpdatePatientCommand(PatientId id, DocumentTypeId documentTypeId, String documentNumber, String firstName, String middleName, String lastName, String secondLastName, LocalDate birthDate, GenderId biologicalSexId, GenderId genderIdentity, String email, String phone, String address, Boolean active, CityMunicipalityId cityId, ProfessionalId updatedBy) {}

