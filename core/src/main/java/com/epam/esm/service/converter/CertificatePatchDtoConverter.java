package com.epam.esm.service.converter;

import com.epam.esm.model.Certificate;
import com.epam.esm.service.dto.CertificateDto;

import java.time.LocalDateTime;

public class CertificatePatchDtoConverter implements DtoConverter<Certificate, CertificateDto> {
    private final CertificateDtoConverter certificateConverter;

    public CertificatePatchDtoConverter() {
        this.certificateConverter = new CertificateDtoConverter();
    }

    public Certificate convertToEntity(CertificateDto certificateDto, Certificate existingCertificate) {
        Certificate certificateFromRequest = certificateConverter.convertToEntity(certificateDto, new Certificate());

        if (certificateFromRequest.getName() != null) {
            existingCertificate.setName(certificateFromRequest.getName());
        }
        if (certificateFromRequest.getPrice() != null) {
            existingCertificate.setPrice(certificateFromRequest.getPrice());
        }
        if (certificateFromRequest.getDescription() != null) {
            existingCertificate.setDescription(certificateFromRequest.getDescription());
        }
        if (certificateFromRequest.getDuration() != null) {
            existingCertificate.setDuration(certificateFromRequest.getDuration());
        }
        existingCertificate.setDateOfModification(LocalDateTime.now());

        return existingCertificate;
    }

    @Override
    public CertificateDto convertToDto(Certificate certificate) {
        throw new UnsupportedOperationException("Convert to dto is not permitted");
    }
}
