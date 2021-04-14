package com.epam.esm.service.service;

import com.epam.esm.service.dto.CertificateDto;

import java.util.List;

public interface CertificateService extends CrudService<CertificateDto> {
    List<CertificateDto> read(String tagName, String searchByName, String searchByDescription, String sortBy, String sortOrder);
}
