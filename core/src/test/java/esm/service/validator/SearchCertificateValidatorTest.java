package esm.service.validator;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.SearchCertificateDto;
import esm.service.validator.extension.SearchCertificateValidatorForTesting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class SearchCertificateValidatorTest {

    @InjectMocks
    private SearchCertificateValidatorForTesting validator;

    private static final SearchCertificateDto CORRECT_SEARCH_CERTIFICATE_DTO = SearchCertificateDto.builder()
            .tagName("tag1")
            .fieldsToSortBy("cost,id")
            .sortOrders("-cost")
            .build();
    private static final String FIELDS_TO_SORT_BY_CORRECT = "cost,duration";
    private static final String FIRST_FIELDS_TO_SORT_BY = "incorrectField,duration";
    private static final String SECOND_FIELDS_TO_SORT_BY = "id,  1duration";
    private static final String SORT_ORDERS_CORRECT = "-cost,-duration";
    private static final String SORT_ORDERS_WITH_NOT_EXISTING_FIELD = "-test,-duration";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateTest() {
        validator.validate(CORRECT_SEARCH_CERTIFICATE_DTO);
        Assertions.assertEquals(0, validator.getMessages().size());
    }

    @Test
    void validateFieldsToSortByTest() {
        validator.validateFieldsToSortBy(FIELDS_TO_SORT_BY_CORRECT);
        Assertions.assertEquals(0, validator.getMessages().size());
    }

    @Test
    void validateIncorrectFieldsToSortByTest() {
        validator.validateFieldsToSortBy(FIRST_FIELDS_TO_SORT_BY);
        Assertions.assertTrue(
                validator.getMessages().contains(ErrorMessage.SEARCH_CERTIFICATE_FIELD_TO_SORT_BY_INCORRECT));
    }

    @Test
    void validateIncorrectSeparationFieldsToSortByTest() {
        validator.validateFieldsToSortBy(SECOND_FIELDS_TO_SORT_BY);
        Assertions.assertTrue(
                validator.getMessages().contains(ErrorMessage.SEARCH_CERTIFICATE_FIELD_TO_SORT_BY_INCORRECT));
    }

    @Test
    void validateSortOrders() {
        validator.validateSortOrders(SORT_ORDERS_CORRECT);
        Assertions.assertEquals(0, validator.getMessages().size());
    }

    @Test
    void validateSortOrdersWithNotExistingField() {
        validator.validateSortOrders(SORT_ORDERS_WITH_NOT_EXISTING_FIELD);
        Assertions.assertTrue(
                validator.getMessages().contains(ErrorMessage.SEARCH_CERTIFICATE_SORT_ORDERS_INCORRECT));
    }

}