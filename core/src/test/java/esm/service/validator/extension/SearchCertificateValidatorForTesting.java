package esm.service.validator.extension;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.validator.SearchCertificateValidator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SearchCertificateValidatorForTesting extends SearchCertificateValidator {
    @Override
    public void validate(SearchCertificateDto searchCertificateDto) {
        super.validate(searchCertificateDto);
    }

    @Override
    public void validateFieldsToSortBy(String fields) {
        super.validateFieldsToSortBy(fields);
    }

    @Override
    public void validateSortOrders(String sortOrders) {
        super.validateSortOrders(sortOrders);
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return super.getMessages();
    }
}
