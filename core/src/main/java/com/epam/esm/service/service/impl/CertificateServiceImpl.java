package com.epam.esm.service.service.impl;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.model.enumeration.RequestedResource;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specification.CertificateSpecification;
import com.epam.esm.service.converter.CertificatePatchDtoConverter;
import com.epam.esm.service.converter.CertificateUpdateDtoConverter;
import com.epam.esm.service.converter.TagDtoConverter;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.CertificateService;
import com.epam.esm.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateUpdateDtoConverter certificateConverter;
    private final TagDtoConverter tagConverter;
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final Validator<CertificateDto> certificateDtoValidator;
    private final Validator<SearchCertificateDto> searchCertificateDtoValidator;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  TagRepository tagRepository,
                                  Validator<CertificateDto> certificateDtoValidator,
                                  Validator<SearchCertificateDto> searchCertificateDtoValidator
    ) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.certificateConverter = new CertificateUpdateDtoConverter();
        this.tagConverter = new TagDtoConverter();
        this.certificateDtoValidator = certificateDtoValidator;
        this.searchCertificateDtoValidator = searchCertificateDtoValidator;
    }

    @Transactional
    @Override
    public CertificateDto create(CertificateDto certificateDto) throws ValidationException {
        certificateDtoValidator.validate(certificateDto);
        if (!certificateDtoValidator.getMessages().isEmpty()) {
            throw new ValidationException(certificateDtoValidator.getMessages(), RequestedResource.CERTIFICATE);
        }

        Certificate createdCertificate = certificateRepository.create(certificateConverter.convertToEntity(certificateDto));

        CertificateDto createdCertificateDto = certificateConverter.convertToDto(createdCertificate);
        createdCertificateDto.setTags(attachTagsToCertificate(createdCertificate.getId(), certificateDto.getTags()));
        return certificateConverter.convertToDto(createdCertificate);
    }


    @Transactional
    @Override
    public CertificateDto update(CertificateDto certificateDto) throws ValidationException {
        certificateDtoValidator.validate(certificateDto);
        if (!certificateDtoValidator.getMessages().isEmpty()) {
            throw new ValidationException(certificateDtoValidator.getMessages(), RequestedResource.CERTIFICATE);
        }

        if (certificateRepository.findById(certificateDto.getId()).isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND,
                    RequestedResource.CERTIFICATE, certificateDto.getId());
        }

        Certificate certificateForUpdate = certificateConverter.convertToEntity(certificateDto);
        certificateRepository.update(certificateForUpdate);
        certificateRepository.detachTagsFromCertificate(certificateDto.getId());
        List<TagDto> attachedTags = attachTagsToCertificate(certificateDto.getId(), certificateDto.getTags());
        certificateForUpdate.setTags(
                attachedTags.stream().map(tagConverter::convertToEntity).collect(Collectors.toList()));
        return certificateConverter.convertToDto(certificateForUpdate);
    }

    @Transactional
    @Override
    public CertificateDto patch(CertificateDto certificateDto) throws ValidationException {
        //TODO validation without name
//        certificateDtoValidator.validate(certificateDto);
//        if (!certificateDtoValidator.getMessages().isEmpty()) {
//            throw new ValidationException(certificateDtoValidator.getMessages(), RequestedResource.CERTIFICATE);
//        }

        Certificate existingCertificate = certificateRepository
                .findById(certificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND,
                        RequestedResource.CERTIFICATE, certificateDto.getId()));

        CertificatePatchDtoConverter converter = new CertificatePatchDtoConverter();
        Certificate certificateForUpdate = converter.convertToEntity(certificateDto, existingCertificate);
        certificateRepository.update(certificateForUpdate);

        if (!certificateDto.getTags().isEmpty()) {
            certificateRepository.detachTagsFromCertificate(certificateForUpdate.getId());
            List<TagDto> attachedTags = attachTagsToCertificate(certificateForUpdate.getId(), certificateDto.getTags());
            certificateForUpdate.setTags(
                    attachedTags.stream().map(tagConverter::convertToEntity).collect(Collectors.toList()));
        }
        return certificateConverter.convertToDto(certificateForUpdate);
    }

    @Override
    public void delete(Long certificateId) {
        certificateRepository.findById(certificateId).ifPresentOrElse(certificate -> certificateRepository.delete(certificateId), () -> {
            throw new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, RequestedResource.CERTIFICATE,
                    certificateId);
        });
    }

    @Override
    public CertificateDto findById(Long certificateId) {
        Certificate certificate = certificateRepository.findById(certificateId).orElseThrow(() -> {
            throw new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, RequestedResource.CERTIFICATE,
                    certificateId);
        });
        certificate.setTags(tagRepository.findByCertificateId(certificateId));
        return certificateConverter.convertToDto(certificate);
    }

    @Override
    public List<CertificateDto> findAllByParams(SearchCertificateDto searchCertificateDto) throws ValidationException {
        searchCertificateDtoValidator.validate(searchCertificateDto);
        if (!searchCertificateDtoValidator.getMessages().isEmpty()) {
            throw new ValidationException(searchCertificateDtoValidator.getMessages(), RequestedResource.CERTIFICATE);
        }

        List<Certificate> certificates = certificateRepository.findAll(new CertificateSpecification(searchCertificateDto));
        certificates.forEach(certificate -> certificate.setTags(
                tagRepository.findByCertificateId(certificate.getId())));
        return certificates.stream()
                .map(certificateConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagDto> attachTagsToCertificate(Long certificateId, List<TagDto> tags) {
        tags.forEach(tag -> {
            tag.setId(tagRepository.createOrGet(tagConverter.convertToEntity(tag)).getId());
            certificateRepository.attachTagToCertificate(certificateId, tag.getId());
        });
        return tags;
    }
}
