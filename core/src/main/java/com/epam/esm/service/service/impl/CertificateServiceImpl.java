package com.epam.esm.service.service.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final DtoConverter<Certificate, CertificateDto> certificateConverter;
    private final DtoConverter<Tag, TagDto> tagConverter;
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  TagRepository tagRepository,
                                  DtoConverter<Certificate, CertificateDto> certificateConverter,
                                  DtoConverter<Tag, TagDto> tagConverter) {

        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.certificateConverter = certificateConverter;
        this.tagConverter = tagConverter;
    }

    //TODO transactional
    @Override
    public CertificateDto create(CertificateDto entity) {
        entity.setDateOfCreation(LocalDateTime.now());
        entity.setDateOfModification(LocalDateTime.now());
        Certificate certificateToCreate = certificateConverter.unconvert(entity);
        Certificate createdCertificate = certificateRepository.create(certificateToCreate);
        createdCertificate.setTags(attachTagsToCertificate(createdCertificate.getId(), certificateToCreate.getTags()));
        return certificateConverter.convert(createdCertificate);
    }

    @Override
    public CertificateDto update(CertificateDto entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public CertificateDto findById(Long id) {
        return null;
    }

    @Override
    public List<CertificateDto> read(String tagName, String searchByName, String searchByDescription, String sortBy, String sortOrder) {
        return null;
    }

    private Set<Tag> attachTagsToCertificate(Long certificateId, Set<Tag> tags) {
        tags.forEach(tag -> {
            Optional<Tag> tagOptional = tagRepository.findByName(tag.getName());
            if (tagOptional.isEmpty()) {
                Tag createdTag = tagRepository.create(tag);
                tag.setId(createdTag.getId());
            } else {
                Tag existingTag = tagOptional.get();
                tag.setId(existingTag.getId());
            }
            certificateRepository.attachTagToCertificate(certificateId, tag.getId());
        });
        return tags;
    }
}
