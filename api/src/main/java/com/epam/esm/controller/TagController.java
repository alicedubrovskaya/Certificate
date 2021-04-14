package com.epam.esm.controller;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagServiceImpl) {
        this.tagService = tagServiceImpl;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        tagDto = tagService.create(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTag(@PathVariable("id") Long id) {
        TagDto tagDto = tagService.findById(id);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
