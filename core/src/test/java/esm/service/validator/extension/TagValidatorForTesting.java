package esm.service.validator.extension;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.validator.TagValidator;
import com.epam.esm.service.validator.Validator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TagValidatorForTesting extends TagValidator {
    @Override
    public void validate(TagDto tagDto) {
        super.validate(tagDto);
    }

    @Override
    public void validateDto(TagDto tagDto) {
        super.validateDto(tagDto);
    }

    @Override
    public void validateName(String name) {
        super.validateName(name);
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return super.getMessages();
    }
}
