package esm.service.validator;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.TagDto;
import esm.service.validator.extension.TagValidatorForTesting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class TagValidatorTest {

    @InjectMocks
    private TagValidatorForTesting tagValidator;

    private static final TagDto FIRST_TAG_DTO = new TagDto(1L, "Tag1");
    private static final String INCORRECT_NAME = "n";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateTest() {
        tagValidator.validate(FIRST_TAG_DTO);
        Assertions.assertEquals(0, tagValidator.getMessages().size());
    }

    @Test
    void validateDtoTest() {
        tagValidator.validate(null);
        Assertions.assertTrue(tagValidator.getMessages().contains(ErrorMessage.TAG_DTO_EMPTY));
    }

    @Test
    void validateNullNameTest() {
        tagValidator.validateName(null);
        Assertions.assertTrue(tagValidator.getMessages().contains(ErrorMessage.TAG_NAME_EMPTY));
    }

    @Test
    void validateIncorrectNameTest() {
        tagValidator.validateName(INCORRECT_NAME);
        Assertions.assertTrue(tagValidator.getMessages().contains(ErrorMessage.TAG_NAME_INCORRECT));
    }
}