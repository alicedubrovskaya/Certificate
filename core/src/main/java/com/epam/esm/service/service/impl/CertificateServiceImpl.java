package com.epam.esm.service.service.impl;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.service.CertificateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Override
    public CertificateDto create(CertificateDto entity) {
        return null;
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
