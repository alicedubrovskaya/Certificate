package com.epam.esm.controller;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.parser.SearchParamsParser;
import com.epam.esm.service.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

/**
 * The class provides certificate operations
 */
@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateServiceImpl) {
        this.certificateService = certificateServiceImpl;
    }

    /**
     * Gets certificate by id
     *
     * @param id unique identifier of certificate, that should be positive integer number
     * @return found certificate. Response code 200.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> getGiftCertificate(
            @PathVariable("id") @Min(value = 1, message = "Id can be more than 0") Long id) {
        CertificateDto certificateDto = certificateService.findById(id);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    /**
     * Gets filtered certificates
     * @param searchParams
     * @return found certificates. Response code 200.
     */
    @GetMapping
    public ResponseEntity<List<CertificateDto>> getGiftCertificates(
            @RequestParam Map<String, String> searchParams) {
        SearchCertificateDto searchCertificateDto = SearchCertificateDto.builder()
                .tagName(searchParams.get("tag"))
                .certificateName(searchParams.get("certificateName"))
                .description(searchParams.get("certificateDescription"))
                .fieldsToSortBy(searchParams.get("fieldsToSortBy"))
                .sortOrders(searchParams.get("sortOrders"))
                .build();
        List<CertificateDto> certificates = certificateService.findAllByParams(searchCertificateDto);
        return new ResponseEntity<>(certificates,HttpStatus.OK);
    }

    /**
     * Creates certificate
     *
     * @param certificateDto should be valid. Otherwise, certificate will not be created.
     * @return ResponseEntity which contains created certificate with generated id. Response code 201.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> createGiftCertificate(@RequestBody CertificateDto certificateDto) {
        certificateDto = certificateService.create(certificateDto);
        return new ResponseEntity<>(certificateDto, HttpStatus.CREATED);
    }

    /**
     * Wholly updates certificate
     *
     * @param id             unique identifier of certificate, that should be positive integer number
     * @param certificateDto
     * @return ResponseEntity which contains updated certificate. Response code 201.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> updateGiftCertificate(
            @PathVariable @Min(value = 1, message = "Id can be more than 0") Long id,
            @RequestBody CertificateDto certificateDto) {
        certificateDto.setId(id);
        certificateDto = certificateService.update(certificateDto);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    /**
     * Partially updates certificate
     *
     * @param id             unique identifier of certificate, that should be positive integer number
     * @param certificateDto
     * @return ResponseEntity which contains updated certificate. Response code 201.
     */
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> patchGiftCertificate(
            @PathVariable @Min(value = 1, message = "Id can be more than 0") Long id,
            @RequestBody CertificateDto certificateDto) {
        certificateDto.setId(id);
        certificateDto = certificateService.patch(certificateDto);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    /**
     * Deletes certificate by id
     *
     * @param id unique identifier of certificate, that should be positive integer number
     * @return Response code 204.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteGiftCertificate(
            @PathVariable @Min(value = 1, message = "Id can be more than 0") Long id) {
        certificateService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
