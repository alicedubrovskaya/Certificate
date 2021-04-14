package com.epam.esm.service.converter;

import com.epam.esm.model.Certificate;
import com.epam.esm.service.dto.CertificateDto;
import org.springframework.stereotype.Component;

@Component
public class CertificateDtoConverter implements DtoConverter<Certificate, CertificateDto> {
    @Override
    public CertificateDto convert(Certificate certificate) {
        return null;
    }

    @Override
    public Certificate unconvert(CertificateDto certificateDto) {
        return null;
    }
}
