package esm.service.validator.extension;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PriceDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.validator.CertificatePatchValidator;
import com.epam.esm.service.validator.CertificateValidator;
import com.epam.esm.service.validator.Validator;

import java.time.Duration;
import java.util.List;

public class CertificatePatchValidatorForTesting extends CertificatePatchValidator {
    public CertificatePatchValidatorForTesting(Validator<TagDto> tagValidator) {
        super(tagValidator);
    }

    @Override
    public void validate(CertificateDto certificateDto) {
        super.validate(certificateDto);
    }

    @Override
    public void validateName(String name) {
        super.validateName(name);
    }

    @Override
    public void validateDto(CertificateDto certificateDto) {
        super.validateDto(certificateDto);
    }

    @Override
    public void validateDescription(String description) {
        super.validateDescription(description);
    }

    @Override
    public void validatePrice(PriceDto priceDto) {
        super.validatePrice(priceDto);
    }

    @Override
    public void validateDuration(Duration duration) {
        super.validateDuration(duration);
    }

    @Override
    public void validateTags(List<TagDto> tags) {
        super.validateTags(tags);
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return super.getMessages();
    }
}
