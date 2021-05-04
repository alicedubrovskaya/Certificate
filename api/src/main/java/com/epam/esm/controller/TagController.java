package com.epam.esm.controller;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * The class provides tag operations
 */
@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagServiceImpl) {
        this.tagService = tagServiceImpl;
    }

    /**
     * Creates tag if it doesn't exist , otherwise returns already existing tag
     *
     * @param tagDto should be valid. Otherwise, tag will not be created.
     * @return ResponseEntity which contains created tag with generated id. Response code 201.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        tagDto = tagService.create(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.CREATED);
    }

    /**
     * Gets tag by id
     *
     * @param id unique identifier of tag, that should be positive integer number
     * @return found TagDto. Response code 200.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTag(
            @PathVariable("id") @Min(value = 1, message = "Id can be more than 0") Long id) {
        TagDto tagDto = tagService.findById(id);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    /**
     * Gets all tags
     *
     * @return found tags. Response code 200.
     */
    @GetMapping
    public ResponseEntity<List<TagDto>> getTags() {
        List<TagDto> tags = tagService.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /**
     * Deletes tag by id
     *
     * @param id unique identifier of tag, that should be positive integer number
     * @return Response code 204.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteTag(
            @PathVariable @Min(value = 1, message = "Id can be more than 0") Long id) {
        tagService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
