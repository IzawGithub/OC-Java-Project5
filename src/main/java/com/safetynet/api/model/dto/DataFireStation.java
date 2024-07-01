package com.safetynet.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DataFireStation {
    @NotBlank
    private String address;
    @NotBlank
    private String station;
}
