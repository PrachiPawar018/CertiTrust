package com.certitrust.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "certificate_id", nullable = false, length = 50, unique = true)
    private String certificateId;

    @Column(name = "recipient_name", nullable = false, length = 150)
    private String recipientName;

    @Column(name = "course_name", nullable = false, length = 200)
    private String courseName;

    @Column(name = "issuing_organization", nullable = false, length = 200)
    private String issuingOrganization;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CertificateStatus status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Certificate() {
    }

    public Certificate(String certificateId, String recipientName, String courseName,
                       String issuingOrganization, LocalDate issueDate, String description,
                       CertificateStatus status) {
        this.certificateId = certificateId;
        this.recipientName = recipientName;
        this.courseName = courseName;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public String getDescription() {
        return description;
    }

    public CertificateStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}