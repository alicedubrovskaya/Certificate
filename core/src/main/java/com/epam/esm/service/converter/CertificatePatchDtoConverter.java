package com.epam.esm.service.converter;

import com.epam.esm.model.Certificate;
import com.epam.esm.service.dto.CertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class CertificatePatchDtoConverter {
    private final CertificateUpdateDtoConverter certificateConverter;

    public CertificatePatchDtoConverter() {
        this.certificateConverter = new CertificateUpdateDtoConverter();
    }

    public Certificate convertToEntity(CertificateDto certificateDto, Certificate existingCertificate) {
        Certificate certificateFromRequest = certificateConverter.convertToEntity(certificateDto);

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
}
