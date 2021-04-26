package com.epam.esm.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TagDto {
    private Long id;

    @NotBlank
    private String name;
}
