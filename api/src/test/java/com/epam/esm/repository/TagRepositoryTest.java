package com.epam.esm.repository;

import com.epam.esm.configuration.TestRepositoryConfiguration;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestRepositoryConfiguration.class})
public class TagRepositoryTest {

    @Autowired
    TagRepository tagRepository;

    @Test
    public void createTagTest() {
//        Tag actualTag = new Tag();
//        actualTag.setId(1L);
//        actualTag.setName("tag1");
//        Long id = tagRepository.create(actualTag).getId();
//        Assertions.assertTrue(tagRepository.findById(id).isPresent());
    }
}
