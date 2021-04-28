package com.epam.esm.service.validator;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.service.dto.TagDto;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TagValidator implements Validator<TagDto> {
    private final List<ErrorMessage> errors = new ArrayList<>();

    @Override
    public void validate(TagDto tagDto) {
        validateDto(tagDto);
        validateName(tagDto);
    }

    protected void validateDto(TagDto tagDto) {
        if (tagDto == null) {
            errors.add(ErrorMessage.TAG_DTO_EMPTY);
        }
    }

    protected void validateName(TagDto tagDto) {
        String name = tagDto.getName();
        if (name == null) {
            errors.add(ErrorMessage.TAG_NAME_EMPTY);
        }

        if (name != null && !(name.length() > 1 && name.length() <= 255)) {
            errors.add(ErrorMessage.TAG_NAME_INCORRECT);
        }
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return errors;
    }
}
