package com.migracion.rangel.application.patientallergy.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.patientallergy.model.aggregate.PatientAllergy;
import com.migracion.rangel.domain.patientallergy.port.repository.PatientAllergyRepository;
import com.migracion.rangel.application.patientallergy.command.UpdatePatientAllergyCommand;
import com.migracion.rangel.application.patientallergy.dto.PatientAllergyResponse;
import com.migracion.rangel.application.patientallergy.exception.PatientAllergyNotFoundApplicationException;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
public class UpdatePatientAllergyUseCase {
    private final PatientAllergyRepository repository;
    private final PatientRepository patients;
    private final ProfessionalRepository professionals;
    public UpdatePatientAllergyUseCase(PatientAllergyRepository repository, PatientRepository patients, ProfessionalRepository professionals) {
        this.repository = Objects.requireNonNull(repository);
        this.patients = Objects.requireNonNull(patients);
        this.professionals = Objects.requireNonNull(professionals);
    }
    public PatientAllergyResponse execute(UpdatePatientAllergyCommand command) {
        var aggregate = repository.findById(command.id()).orElseThrow(() -> new PatientAllergyNotFoundApplicationException(command.id()));
        patients.findById(command.patientId()).orElseThrow(() -> new PatientNotFoundApplicationException(command.patientId()));
        professionals.findById(command.recordedBy()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.recordedBy()));
        aggregate.update(command.patientId(), command.substance(), command.reaction(), command.severity(), command.active(), command.recordedAt(), command.recordedBy());
        return PatientAllergyResponse.from(repository.save(aggregate));
    }
}
