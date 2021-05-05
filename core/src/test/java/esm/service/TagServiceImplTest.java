package esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.Tag;
import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.impl.TagServiceImpl;
import com.epam.esm.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(FIRST_TAG));
        Mockito.when(converter.convertToDto(Mockito.any(Tag.class))).thenReturn(FIRST_TAG_DTO);
        TagDto tagDto = service.findById(1L);
        Assertions.assertEquals(FIRST_TAG_DTO, tagDto);
    }

    @Test
    public void shouldNotGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    public void shouldDeleteTagById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(FIRST_TAG));
        Mockito.when(repository.delete(Mockito.anyLong())).thenReturn(true);
        service.delete(1L);
        verify(repository).delete(1L);
    }

    @Test
    public void shouldNotDeleteTagById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    public void shouldFindAll() {
        service.findAll();
        verify(repository).findAll();
    }

    @Test
    public void shouldCreateTag() throws ValidationException {
        Mockito.when(converter.convertToEntity(Mockito.any(TagDto.class), Mockito.any(Tag.class))).thenReturn(FIRST_TAG);
        Mockito.when(repository.create(Mockito.any(Tag.class))).thenReturn(FIRST_TAG);
        Mockito.when(converter.convertToDto(Mockito.any(Tag.class))).thenReturn(FIRST_TAG_DTO);

        TagDto tagDto = service.create(FIRST_TAG_DTO);
        Assertions.assertEquals(FIRST_TAG_DTO, tagDto);
    }

    @Test
    public void shouldNotCreateTag() {
        Mockito.when(validator.getMessages()).thenReturn(errorMessages);
        Assertions.assertThrows(ValidationException.class, () -> service.create(SECOND_TAG_DTO));
    }
}
