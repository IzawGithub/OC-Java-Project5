package com.safetynet.api.model.dto;

import com.safetynet.api.model.FireStation;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DataUpdateFireStation {
    @NotNull
    FireStation old;
    @NotNull
    FireStation updated;
}
