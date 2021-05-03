package com.epam.esm.service.service;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.dto.TagDto;

import java.util.List;

public interface CertificateService {
    CertificateDto create(CertificateDto certificateDto);

    CertificateDto update(CertificateDto certificateDto);

    CertificateDto patch(CertificateDto certificateDto);

    void delete(Long certificateId);

    CertificateDto findById(Long certificateId);

    List<CertificateDto> findAllByParams(SearchCertificateDto searchCertificateDto);

    List<TagDto> attachTagsToCertificate(Long certificateId, List<TagDto> tags);
}
