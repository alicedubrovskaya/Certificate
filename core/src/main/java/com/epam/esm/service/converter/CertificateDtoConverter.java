package com.epam.esm.service.converter;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CertificateDtoConverter implements DtoConverter<Certificate, CertificateDto> {
    private final DtoConverter<Tag, TagDto> converter;

    @Autowired
    public CertificateDtoConverter(DtoConverter<Tag, TagDto> converter) {
        this.converter = converter;
    }

    @Override
    public CertificateDto convert(Certificate certificate) {
        CertificateDto certificateDto = new CertificateDto();
        if (certificate != null) {
            certificateDto.setId(certificate.getId());
            certificateDto.setDateOfCreation(certificate.getDateOfCreation());
            certificateDto.setDateOfModification(certificate.getDateOfModification());
            certificateDto.setDuration(certificate.getDuration());
            certificateDto.setName(certificate.getName());
            certificateDto.setDescription(certificate.getDescription());
            certificateDto.setPrice(certificate.getPrice());
            Set<TagDto> tagDTO = certificate.getTags()
                    .stream()
                    .map(converter::convert)
                    .collect(Collectors.toSet());
            certificateDto.setTags(tagDTO);
        }
        return certificateDto;
    }

    @Override
    public Certificate unconvert(CertificateDto certificateDto) {
        Certificate certificate = new Certificate();
        if (certificateDto != null) {
            certificate.setId(certificateDto.getId());
            certificate.setDateOfModification(certificateDto.getDateOfModification());
            certificate.setDateOfCreation(certificateDto.getDateOfCreation());
            certificate.setDuration(certificateDto.getDuration());
            certificate.setName(certificateDto.getName());
            certificate.setDescription(certificateDto.getDescription());
            certificate.setPrice(certificateDto.getPrice());
            if (certificateDto.getTags() != null) {
                certificate.setTags(certificateDto.getTags()
                        .stream()
                        .map(converter::unconvert)
                        .collect(Collectors.toSet()));
            }
        }
        return certificate;
    }
}
