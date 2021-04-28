package com.epam.esm.service.validator;

import com.epam.esm.model.enumeration.ErrorMessage;

import java.util.List;

public interface Validator<Entity> {
    void validate(Entity entity);

    List<ErrorMessage> getMessages();
}
