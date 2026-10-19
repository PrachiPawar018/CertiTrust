package com.certitrust;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.certitrust.entity.Certificate;
import com.certitrust.entity.CertificateStatus;
import com.certitrust.repository.CertificateRepository;
import com.certitrust.service.CertificateVerificationService;

@SpringBootTest
@AutoConfigureMockMvc
class CertiTrustApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CertificateRepository certificateRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void homePageRendersCertificateLookup() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("home"))
				.andExpect(content().string(containsString("Enter the certificate reference")));
	}

	@Test
	void verificationPageRetainsEnteredCertificateId() throws Exception {
		mockMvc.perform(get("/verify").param("certificateId", "CT-2026-AB12CD"))
				.andExpect(status().isOk())
				.andExpect(view().name("verify"))
				.andExpect(content().string(containsString("CT-2026-AB12CD")));
	}

	@Test
	void verificationDisplaysValidCertificateDetailsAndTrimsId() throws Exception {
		certificateRepository.save(new Certificate("TEST-VALID-001", "Test Recipient", "Test Course",
				"Test Organization", LocalDate.of(2026, 1, 15), "Completion", CertificateStatus.VALID));

		mockMvc.perform(get("/verify").param("certificateId", "  TEST-VALID-001  "))
				.andExpect(status().isOk())
				.andExpect(view().name("verify"))
				.andExpect(content().string(containsString("Certificate is valid")))
				.andExpect(content().string(containsString("Test Recipient")))
				.andExpect(content().string(containsString("Test Course")));
	}

	@Test
	void verificationWarnsForRevokedCertificate() throws Exception {
		certificateRepository.save(new Certificate("TEST-REVOKED-001", "Revoked Recipient", "Test Course",
				"Test Organization", LocalDate.of(2026, 1, 15), null, CertificateStatus.REVOKED));

		mockMvc.perform(get("/verify").param("certificateId", "TEST-REVOKED-001"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Certificate has been revoked")))
				.andExpect(content().string(containsString("REVOKED")));
	}

	@Test
	void verificationReportsUnknownAndBlankIds() throws Exception {
		mockMvc.perform(get("/verify").param("certificateId", "UNKNOWN-001"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Certificate not found")));

		mockMvc.perform(get("/verify").param("certificateId", "   "))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("The ID cannot be blank")));
	}

	@Test
	void verificationRequiresExactIdCaseEvenWithCaseInsensitiveDatabaseMatch() {
		CertificateRepository repository = mock(CertificateRepository.class);
		Certificate storedCertificate = new Certificate("CT-2026-0001", "Test Recipient", "Test Course",
				"Test Organization", LocalDate.of(2026, 1, 15), null, CertificateStatus.VALID);
		when(repository.findByCertificateId("ct-2026-0001")).thenReturn(Optional.of(storedCertificate));

		CertificateVerificationService service = new CertificateVerificationService(repository);

		assertTrue(service.findByCertificateId("ct-2026-0001").isEmpty());
	}

}
