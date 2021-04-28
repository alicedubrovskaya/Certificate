package com.epam.esm.service.service;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.TagDto;

import java.util.List;
import java.util.Set;

public interface CertificateService extends CrudService<CertificateDto> {
    List<CertificateDto> findAllByParams(String tagName, String searchByName, String searchByDescription, String sortBy, String sortOrder);

    Set<TagDto> attachTagsToCertificate(Long certificateId, Set<TagDto> tags);
}
