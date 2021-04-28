package com.epam.esm.service.converter;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Price;
import com.epam.esm.model.Tag;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PriceDto;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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
        Set<TagDto> tagDtos = new HashSet<>();
        if (certificate.getTags() != null) {
            tagDtos = certificate.getTags().stream()
                    .map(converter::convert)
                    .collect(Collectors.toSet());
        }
        return CertificateDto.builder()
                .id(certificate.getId())
                .dateOfCreation(certificate.getDateOfCreation())
                .dateOfModification(certificate.getDateOfModification())
                .duration(certificate.getDuration())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(new PriceDto(certificate.getPrice().getCost(), certificate.getPrice().getCurrency()))
                .tags(tagDtos)
                .build();
    }

    @Override
    public Certificate unconvert(CertificateDto certificateDto) {
        Set<Tag> tags = new HashSet<>();
        if (certificateDto.getTags() != null) {
            tags = certificateDto.getTags().stream()
                    .map(converter::unconvert)
                    .collect(Collectors.toSet());
        }
        return Certificate.builder()
                .id(certificateDto.getId())
                .dateOfCreation(certificateDto.getDateOfCreation())
                .dateOfModification(certificateDto.getDateOfModification())
                .duration(certificateDto.getDuration())
                .name(certificateDto.getName())
                .description(certificateDto.getDescription())
                .price(new Price(certificateDto.getPrice().getCost(), certificateDto.getPrice().getCurrency()))
                .tags(tags)
                .build();
    }
}
