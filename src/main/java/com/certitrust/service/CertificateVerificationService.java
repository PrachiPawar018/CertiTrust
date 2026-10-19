package com.certitrust.service;

import com.certitrust.entity.Certificate;
import com.certitrust.repository.CertificateRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

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
        return certificateRepository.findByCertificateId(certificateId.trim());
    }
}