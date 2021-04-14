package com.epam.esm.controller;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateServiceImpl) {
        this.certificateService = certificateServiceImpl;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> getGiftCertificate(@PathVariable("id") Long id) {
        CertificateDto certificateDto = certificateService.findById(id);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CertificateDto>> getGiftCertificate(
            @RequestParam(value = "tagName", required = false) String tagName,
            @RequestParam(value = "certificateName", required = false) String certificateName,
            @RequestParam(value = "certificateDescription", required = false) String certificateDescription,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "sortOrder", required = false) String sortOrder) {

        List<CertificateDto> certificates = certificateService.read(tagName, certificateName, certificateDescription,
                sortBy, sortOrder);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> createGiftCertificate(@RequestBody CertificateDto certificateDto) {
        certificateDto = certificateService.create(certificateDto);
        return new ResponseEntity<>(certificateDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> updateGiftCertificate(@PathVariable Long id, @RequestBody CertificateDto certificateDto) {
        certificateDto.setId(id);
        certificateDto = certificateService.update(certificateDto);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteGiftCertificate(@PathVariable Long id) {
        certificateService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
