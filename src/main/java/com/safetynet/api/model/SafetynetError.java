package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.api.exception.CRUDException.ECRUDException;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonPropertyOrder(alphabetic = true)
public class SafetynetError {
    @NotBlank
    private ECRUDException error;
    @NotBlank
    private String message;
}
