package com.epam.esm.service.converter.mapper;

import com.epam.esm.model.Certificate;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.dto.CertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Mapper {
    private final DtoConverter<Certificate, CertificateDto> certificateConverter;

    @Autowired
    public Mapper(DtoConverter<Certificate, CertificateDto> certificateConverter) {
        this.certificateConverter = certificateConverter;
    }

    public Certificate map(CertificateDto certificateDto, Certificate existingCertificate) {
        Certificate certificateFromRequest = certificateConverter.unconvert(certificateDto);

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
