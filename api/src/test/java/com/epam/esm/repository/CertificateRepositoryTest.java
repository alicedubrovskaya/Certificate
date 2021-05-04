package com.epam.esm.repository;

import com.epam.esm.configuration.TestRepositoryConfiguration;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Price;
import com.epam.esm.model.enumeration.Currency;
import com.epam.esm.repository.specification.CertificateSpecification;
import com.epam.esm.service.dto.SearchCertificateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestRepositoryConfiguration.class})
public class CertificateRepositoryTest {
    @Autowired
    CertificateRepository certificateRepository;

    private static final Certificate SECOND_CERTIFICATE = Certificate.builder()
            .id(2L)
            .name("Certificate2")
            .description("Second Description")
            .price(new Price(new BigDecimal(3), Currency.BYN))
            .duration(Duration.ofDays(8))
            .dateOfCreation(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .dateOfModification(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .build();

    @Test
    public void createCertificateTest() {
        Certificate newCertificate = Certificate.builder()
                .name("Certificate1")
                .description("first description")
                .price(new Price(BigDecimal.valueOf(100d), Currency.BYN))
                .duration(Duration.ofDays(3L))
                .build();
        Long id = certificateRepository.create(newCertificate).getId();
        Assertions.assertTrue(certificateRepository.findById(id).isPresent());
    }

    @Test
    public void attachTagToCertificateTest() {
        boolean attachResult = certificateRepository.attachTagToCertificate(1L, 2L);
        Assertions.assertTrue(attachResult);
    }

    @Test
    public void detachTagToCertificateTest() {
        boolean detachResult = certificateRepository.detachTagsFromCertificate(1L);
        Assertions.assertTrue(detachResult);
    }

    @Test
    public void findByIdCertificateTest() {
        Optional<Certificate> certificate = certificateRepository.findById(1L);
        Assertions.assertTrue(certificate.isPresent());
    }

    @Test
    public void notFindByIdCertificateTest() {
        Optional<Certificate> certificate = certificateRepository.findById(210L);
        Assertions.assertFalse(certificate.isPresent());
    }

    @Test
    public void deleteCertificateTest() {
        boolean deleteResult = certificateRepository.delete(3L);
        Assertions.assertTrue(deleteResult);
    }

    @Test
    public void notDeleteCertificateTest() {
        boolean deleteResult = certificateRepository.delete(210L);
        Assertions.assertFalse(deleteResult);
    }

    @Test
    public void findAllByTagName() {
        List<Certificate> actualCertificates = certificateRepository.findAll(new CertificateSpecification(
                new SearchCertificateDto("relax", null, null,
                        null, null))
        );
        Assertions.assertEquals(actualCertificates.size(), 1);
    }
}
