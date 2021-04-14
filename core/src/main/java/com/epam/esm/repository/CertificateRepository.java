package com.epam.esm.repository;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository {
    Certificate create(Certificate certificate);

    Optional<Certificate> findById(Long id);

    List<Certificate> findAll();

    void delete(Long id);
}
