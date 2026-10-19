package com.certitrust.repository;

import com.certitrust.entity.Certificate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByCertificateId(String certificateId);
}