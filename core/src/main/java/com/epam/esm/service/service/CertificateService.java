package com.epam.esm.service.service;

import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.dto.TagDto;

import java.util.List;
import java.util.Set;

public interface CertificateService {
    CertificateDto create(CertificateDto certificateDto) throws ValidationException;

    CertificateDto update(CertificateDto certificateDto) throws ValidationException ;

    CertificateDto patch(CertificateDto certificateDto) throws ValidationException;

    void delete(Long certificateId);

    CertificateDto findById(Long certificateId);

    List<CertificateDto> findAllByParams(SearchCertificateDto searchCertificateDto) throws ValidationException;

    List<TagDto> attachTagsToCertificate(Long certificateId, List<TagDto> tags);
}
