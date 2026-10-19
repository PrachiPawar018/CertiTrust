package com.certitrust.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.certitrust.entity.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByCertificateId(String certificateId);
}