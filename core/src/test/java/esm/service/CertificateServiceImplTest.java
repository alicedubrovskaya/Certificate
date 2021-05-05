package esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.converter.DtoConverter;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.service.impl.CertificateServiceImpl;
import com.epam.esm.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

public class CertificateServiceImplTest {
    private static final Certificate FIRST_CERTIFICATE = Certificate.builder()
            .id(1L)
            .name("Certificate1")
            .description("First Description")
            .duration(Duration.ofDays(8))
            .dateOfCreation(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .dateOfModification(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .build();

    private static final CertificateDto FIRST_CERTIFICATE_DTO = CertificateDto.builder()
            .id(1L)
            .name("Certificate1")
            .description("First Description")
            .duration(Duration.ofDays(8))
            .dateOfCreation(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .dateOfModification(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .tags(new ArrayList<>())
            .build();

    private static final List<ErrorMessage> errorMessages = Collections.singletonList(ErrorMessage.CERTIFICATE_NAME_INCORRECT);

    @InjectMocks
    private CertificateServiceImpl service;

    @Mock
    private CertificateRepository repository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private DtoConverter<Certificate, CertificateDto> converter;

    @Mock
    private Validator<CertificateDto> validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(FIRST_CERTIFICATE));
        Mockito.when(converter.convertToDto(Mockito.any(Certificate.class))).thenReturn(FIRST_CERTIFICATE_DTO);
        Mockito.when(tagRepository.findByCertificateId(1L)).thenReturn(null);
        CertificateDto certificateDto = service.findById(1L);
        Assertions.assertEquals(FIRST_CERTIFICATE_DTO, certificateDto);
    }

    @Test
    public void shouldNotGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () ->  service.findById(1L));
    }

    @Test
    public void shouldDeleteById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(FIRST_CERTIFICATE));
        Mockito.when(repository.delete(Mockito.anyLong())).thenReturn(true);
        service.delete(1L);
        verify(repository).delete(1L);
    }

    @Test
    public void shouldNotDeleteById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () ->  service.delete(6L));
    }

    @Test
    public void shouldCreateCertificate() throws ValidationException {
        Mockito.when(converter.convertToEntity(Mockito.any(CertificateDto.class), Mockito.any(Certificate.class))).thenReturn(FIRST_CERTIFICATE);
        Mockito.when(repository.create(Mockito.any(Certificate.class))).thenReturn(FIRST_CERTIFICATE);
        Mockito.when(converter.convertToDto(Mockito.any(Certificate.class))).thenReturn(FIRST_CERTIFICATE_DTO);

        CertificateDto certificateDto = service.create(FIRST_CERTIFICATE_DTO);
        Assertions.assertEquals(FIRST_CERTIFICATE_DTO, certificateDto);
    }

    @Test
    public void shouldNotCreate() throws ValidationException {
        Mockito.when(validator.getMessages()).thenReturn(errorMessages);
        Assertions.assertThrows(ValidationException.class, () ->  service.create(FIRST_CERTIFICATE_DTO));
    }
}
