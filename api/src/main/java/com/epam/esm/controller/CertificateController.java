package com.epam.esm.controller;

import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

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
    public ResponseEntity<CertificateDto> getGiftCertificate(@PathVariable("id") @Min(1) Long id) {
        CertificateDto certificateDto = certificateService.findById(id);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }


    /**
     * Gets filtered certificates
     *
     * @param tagName                that should contain certificate
     * @param certificateName
     * @param certificateDescription
     * @param sortBy
     * @param sortOrder
     * @return found certificates. Response code 200.
     */
    @GetMapping
    public ResponseEntity<List<CertificateDto>> getGiftCertificates(
            @RequestParam(value = "tagName", required = false) String tagName,
            @RequestParam(value = "certificateName", required = false) String certificateName,
            @RequestParam(value = "certificateDescription", required = false) String certificateDescription,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "sortOrder", required = false) String sortOrder) {

        List<CertificateDto> certificates = certificateService.findAllByParams(tagName, certificateName, certificateDescription,
                sortBy, sortOrder);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    /**
     * Creates certificate
     *
     * @param certificateDto should be valid. Otherwise, certificate will not be created.
     * @return ResponseEntity which contains created certificate with generated id. Response code 201.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> createGiftCertificate(@RequestBody @Valid CertificateDto certificateDto)
            throws ValidationException {
        certificateDto = certificateService.create(certificateDto);
        return new ResponseEntity<>(certificateDto, HttpStatus.CREATED);
    }

    /**
     * Updates certificate
     *
     * @param id             unique identifier of certificate, that should be positive integer number
     * @param certificateDto
     * @return ResponseEntity which contains updated certificate. Response code 201.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> updateGiftCertificate(@PathVariable @Min(1) Long id, @RequestBody @Valid CertificateDto certificateDto) {
        certificateDto.setId(id);
        certificateDto = certificateService.update(certificateDto);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    /**
     * Deletes certificate by id
     *
     * @param id unique identifier of certificate, that should be positive integer number
     * @return Response code 204.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteGiftCertificate(@PathVariable @Min(1) Long id) {
        certificateService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
