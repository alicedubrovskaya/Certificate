package com.epam.esm.service.service.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final DtoConverter<Certificate, CertificateDto> dtoConverter;
    private final CertificateRepository certificateRepository;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, DtoConverter<Certificate, CertificateDto> dtoConverter) {
        this.certificateRepository = certificateRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public CertificateDto create(CertificateDto entity) {
        entity.setDateOfCreation(LocalDateTime.now());
        entity.setDateOfModification(LocalDateTime.now());
        Certificate createdCertificate = certificateRepository.create(dtoConverter.unconvert(entity));
        // attachTags(createdCertificate.getId(), certificateDto.getTags());
        return dtoConverter.convert(createdCertificate);
    }

    @Override
    public CertificateDto update(CertificateDto entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public CertificateDto findById(Long id) {
        return null;
    }

    @Override
    public List<CertificateDto> read(String tagName, String searchByName, String searchByDescription, String sortBy, String sortOrder) {
        return null;
    }
}
