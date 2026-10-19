package com.certitrust.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.certitrust.entity.Certificate;
import com.certitrust.repository.CertificateRepository;

@Service
public class CertificateVerificationService {

    private final CertificateRepository certificateRepository;

    public CertificateVerificationService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public Optional<Certificate> findByCertificateId(String certificateId) {
        if (certificateId == null || certificateId.isBlank()) {
            return Optional.empty();
        }
        String normalizedId = certificateId.trim();
        return certificateRepository.findByCertificateId(normalizedId)
            .filter(certificate -> normalizedId.equals(certificate.getCertificateId()));
    }
}