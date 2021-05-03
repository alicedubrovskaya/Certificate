package com.epam.esm.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Certificate {
    private Long id;
    private String name;
    private String description;
    private Price price;
    private Duration duration;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfModification;
    private List<Tag> tags;
}
