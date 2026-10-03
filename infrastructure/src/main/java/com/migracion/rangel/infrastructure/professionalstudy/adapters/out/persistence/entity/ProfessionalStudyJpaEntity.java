package com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.entity;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
@Entity
@Table(name = "professional_studies")
public class ProfessionalStudyJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "study_id", nullable = false)
    private UUID studyId;

    @Column(name = "professional_id", nullable = false)
    private UUID professionalId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "university", nullable = false, length = 100)
    private String university;

    @Column(name = "is_valid", nullable = false)
    private Boolean isValid;

    @Column(name = "resolution_number", nullable = true, length = 60)
    private String resolutionNumber;

    @Column(name = "country_id", nullable = false)
    private UUID countryId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    public ProfessionalStudyJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getStudyId() { return studyId; }
    public void setStudyId(UUID studyId) { this.studyId = studyId; }

    public UUID getProfessionalId() { return professionalId; }
    public void setProfessionalId(UUID professionalId) { this.professionalId = professionalId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }

    public Boolean getIsValid() { return isValid; }
    public void setIsValid(Boolean isValid) { this.isValid = isValid; }

    public String getResolutionNumber() { return resolutionNumber; }
    public void setResolutionNumber(String resolutionNumber) { this.resolutionNumber = resolutionNumber; }

    public UUID getCountryId() { return countryId; }
    public void setCountryId(UUID countryId) { this.countryId = countryId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
