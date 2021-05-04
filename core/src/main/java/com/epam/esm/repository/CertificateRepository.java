package com.epam.esm.repository;

import com.epam.esm.model.Certificate;
import com.epam.esm.repository.specification.CertificateSpecification;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository {
    Certificate create(Certificate certificate);

    Optional<Certificate> findById(Long id);

    List<Certificate> findAll(CertificateSpecification specification);

    boolean delete(Long id);

    void update(Certificate certificate);

    boolean attachTagToCertificate(Long certificateId, Long tagId);

    boolean detachTagsFromCertificate(Long certificateId);
}
