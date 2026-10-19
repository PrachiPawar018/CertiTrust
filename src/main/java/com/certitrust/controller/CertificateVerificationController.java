package com.certitrust.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.certitrust.entity.Certificate;
import com.certitrust.service.CertificateVerificationService;

@Controller
public class CertificateVerificationController {

    private final CertificateVerificationService verificationService;

    public CertificateVerificationController(CertificateVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping("/verify")
    public String verify(@RequestParam(required = false) String certificateId, Model model) {
        model.addAttribute("certificateId", certificateId == null ? "" : certificateId.trim());
        if (certificateId == null) {
            return "verify";
        }

        String normalizedId = certificateId.trim();
        if (normalizedId.isEmpty()) {
            model.addAttribute("verificationOutcome", "EMPTY");
            return "verify";
        }

        Optional<Certificate> certificate = verificationService.findByCertificateId(normalizedId);
        if (certificate.isPresent()) {
            model.addAttribute("certificate", certificate.get());
            model.addAttribute("verificationOutcome", certificate.get().getStatus().name());
        } else {
            model.addAttribute("verificationOutcome", "NOT_FOUND");
        }
        return "verify";
    }
}