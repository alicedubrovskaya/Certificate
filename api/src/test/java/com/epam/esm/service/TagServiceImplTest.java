package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.Tag;
import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.impl.TagServiceImpl;
import com.epam.esm.service.validator.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.verify;

public class TagServiceImplTest {
    private static final Tag FIRST_TAG = new Tag(1L, "Tag1");
    private static final TagDto FIRST_TAG_DTO = new TagDto(1L, "Tag1");

    private static final TagDto SECOND_TAG_DTO = new TagDto(2L, "T");

    private static final List<ErrorMessage> errorMessages = Collections.singletonList(ErrorMessage.TAG_NAME_INCORRECT);


    @InjectMocks
    private TagServiceImpl service;

    @Mock
    private TagRepository repository;

    @Mock
    private DtoConverter<Tag, TagDto> converter;

    @Mock
    private Validator<TagDto> validator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(FIRST_TAG));
        Mockito.when(converter.convert(Mockito.any(Tag.class))).thenReturn(FIRST_TAG_DTO);
        TagDto tagDto = service.findById(1L);
        Assert.assertEquals(FIRST_TAG_DTO, tagDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldNotGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        service.findById(1L);
    }

    @Test
    public void shouldDeleteTagById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(FIRST_TAG));
        Mockito.when(repository.delete(Mockito.anyLong())).thenReturn(true);
        service.delete(1L);
        verify(repository).delete(1L);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void shouldNotDeleteTagById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        service.delete(6L);
    }

    @Test
    public void shouldFindAll() {
        service.findAll();
        verify(repository).findAll();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotUpdate() {
        service.update(FIRST_TAG_DTO);
    }

    @Test
    public void shouldCreateTag() throws ValidationException {
        Mockito.when(converter.unconvert(Mockito.any(TagDto.class))).thenReturn(FIRST_TAG);
        Mockito.when(repository.create(Mockito.any(Tag.class))).thenReturn(FIRST_TAG);
        Mockito.when(converter.convert(Mockito.any(Tag.class))).thenReturn(FIRST_TAG_DTO);

        TagDto tagDto = service.create(FIRST_TAG_DTO);
        Assert.assertEquals(FIRST_TAG_DTO, tagDto);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotCreateTag() throws ValidationException {
        Mockito.when(validator.getMessages()).thenReturn(errorMessages);
        service.create(SECOND_TAG_DTO);
    }
}
