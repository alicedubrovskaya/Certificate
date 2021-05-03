package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
