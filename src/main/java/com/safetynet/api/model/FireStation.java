package com.safetynet.api.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonPropertyOrder(alphabetic = true)
public class FireStation {
    public interface AddressView {
    }

    public interface StationView {
    }

    public interface DefaultView extends AddressView, StationView {
    }

    @NotBlank
    @JsonView(AddressView.class)
    private Set<String> address;
    @EqualsAndHashCode.Include
    @NotBlank
    @JsonView(StationView.class)
    private String station;
}
