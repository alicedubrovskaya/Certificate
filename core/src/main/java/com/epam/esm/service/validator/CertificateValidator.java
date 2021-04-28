package com.epam.esm.service.validator;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CertificateValidator implements Validator<CertificateDto> {
    private final Validator<TagDto> tagValidator;
    private final List<ErrorMessage> errors = new ArrayList<>();
    //TODO better regex
    private static final String COST_CORRECT = "\\d{1,10}\\.\\d{0,4}";
    private static final String COST_COUNT_OF_CHARACTERS_CORRECT = ".{2,10}";

    @Autowired
    public CertificateValidator(Validator<TagDto> tagValidator) {
        this.tagValidator = tagValidator;
    }

    @Override
    public void validate(CertificateDto certificateDto) {
        validateDto(certificateDto);
        validateName(certificateDto);
        validateDescription(certificateDto);
        validateCost(certificateDto);
        validateDuration(certificateDto);
        validateTags(certificateDto);
    }

    protected void validateDto(CertificateDto certificateDto) {
        if (certificateDto == null) {
            errors.add(ErrorMessage.CERTIFICATE_DTO_EMPTY);
        }
    }

    protected void validateName(CertificateDto certificateDto) {
        String name = certificateDto.getName();
        if (name == null) {
            errors.add(ErrorMessage.CERTIFICATE_NAME_EMPTY);
        }

        if (name != null && !(name.length() > 5 &&
                name.length() < 255)) {
            errors.add(ErrorMessage.TAG_NAME_INCORRECT);
        }
    }

    protected void validateDescription(CertificateDto certificateDto) {
        String description = certificateDto.getDescription();
        if (description != null && !(description.length() >= 10 && description.length() <= 400)) {
            errors.add(ErrorMessage.CERTIFICATE_DESCRIPTION_INCORRECT);
        }
    }

    protected void validateCost(CertificateDto certificateDto) {
        if (certificateDto.getPrice() != null && certificateDto.getPrice().getCost().toString().matches(COST_CORRECT)
                && certificateDto.getPrice().getCost().toString().matches(COST_COUNT_OF_CHARACTERS_CORRECT)) {
            errors.add(ErrorMessage.CERTIFICATE_PRICE_COST_INCORRECT);
        }
    }

    protected void validateDuration(CertificateDto certificateDto) {
        if (certificateDto.getDuration() != null && certificateDto.getDuration().toDays() <= 0) {
            errors.add(ErrorMessage.CERTIFICATE_DURATION_INCORRECT);
        }
    }

    protected void validateTags(CertificateDto certificateDto) {
        if (certificateDto.getTags() != null) {
            certificateDto.getTags().forEach(tagValidator::validate);
            errors.addAll(tagValidator.getMessages());
        }
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return errors;
    }
}
