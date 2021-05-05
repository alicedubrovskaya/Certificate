package esm.service.validator;

import com.epam.esm.model.enumeration.Currency;
import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PriceDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.validator.CertificateValidator;
import com.epam.esm.service.validator.TagValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CertificatePatchValidatorTest {

    @InjectMocks
    private CertificateValidator certificateValidator;

    @Mock
    private TagValidator tagValidator;

    private static final CertificateDto FIRST_CERTIFICATE_DTO = CertificateDto.builder()
            .id(1L)
            .name("Certificate1")
            .description("First Description")
            .duration(Duration.ofDays(8))
            .dateOfCreation(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .dateOfModification(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .build();
    private static final String INCORRECT_NAME = "hell";
    private static final String INCORRECT_DESCRIPTION = "testing";
    private static final PriceDto PRICE_WITH_EMPTY_FIELD = new PriceDto(null, Currency.BYN);
    private static final PriceDto PRICE_WITH_INCORRECT_COST = new PriceDto(new BigDecimal("13.1000002"), Currency.BYN);
    private static final Duration INCORRECT_DURATION = Duration.ofDays(-4);
    private static final List<TagDto> EMPTY_TAGS_DTO = Arrays.asList(null, null);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validateTest() {
        certificateValidator.validate(FIRST_CERTIFICATE_DTO);
        Assertions.assertEquals(0, certificateValidator.getMessages().size());
    }

    @Test
    public void validateNullDtoTest() {
        certificateValidator.validate(null);
        Assertions.assertTrue(certificateValidator.getMessages().contains(ErrorMessage.CERTIFICATE_DTO_EMPTY));
    }

    @Test
    public void validateIncorrectNameTest() {
        certificateValidator.validateName(INCORRECT_NAME);
        Assertions.assertTrue(certificateValidator.getMessages().contains(ErrorMessage.CERTIFICATE_NAME_INCORRECT));
    }

    @Test
    public void validateIncorrectDescriptionTest() {
        certificateValidator.validateDescription(INCORRECT_DESCRIPTION);
        Assertions.assertTrue(certificateValidator.getMessages().contains(ErrorMessage.CERTIFICATE_DESCRIPTION_INCORRECT));
    }

    @Test
    public void validateNullDescriptionTest() {
        certificateValidator.validateDescription(null);
        Assertions.assertEquals(0, certificateValidator.getMessages().size());
    }

    @Test
    public void validatePriceWithEmptyFieldTest() {
        certificateValidator.validatePrice(PRICE_WITH_EMPTY_FIELD);
        Assertions.assertTrue(certificateValidator.getMessages().contains(ErrorMessage.CERTIFICATE_PRICE_FIELD_EMPTY));
    }

    @Test
    public void validatePriceWithIncorrectCostTest() {
        certificateValidator.validatePrice(PRICE_WITH_INCORRECT_COST);
        Assertions.assertTrue(certificateValidator.getMessages().contains(ErrorMessage.CERTIFICATE_PRICE_COST_INCORRECT));
    }

    @Test
    public void validateIncorrectDurationTest() {
        certificateValidator.validateDuration(INCORRECT_DURATION);
        Assertions.assertTrue(certificateValidator.getMessages().contains(ErrorMessage.CERTIFICATE_DURATION_INCORRECT));
    }

    @Test
    public void validateEmptyTagsTest() {
        Mockito.when(tagValidator.getMessages()).thenReturn(Arrays.asList(
                ErrorMessage.TAG_DTO_EMPTY, ErrorMessage.TAG_DTO_EMPTY));
        certificateValidator.validateTags(EMPTY_TAGS_DTO);
        Assertions.assertEquals(2, certificateValidator.getMessages().size());
    }
}