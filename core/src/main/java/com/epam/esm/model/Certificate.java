package com.epam.esm.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class Certificate {
    private Long id;
    private String name;
    private String description;
    private Price price;
    private Duration duration;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfModification;
    private Set<Tag> tags = new HashSet<>();
}
