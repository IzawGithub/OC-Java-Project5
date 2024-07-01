package com.safetynet.api.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import com.safetynet.api.model.Person;

@Builder
@Data
@JsonPropertyOrder(alphabetic = true)
public class DataChildAlert {

    public interface AgeView {
    }

    public interface ChildView extends Person.URLsChildAlertView {
    }

    public interface FamilyView {
    }

    public interface URLsControllerView extends AgeView, ChildView, FamilyView {
    }

    @NotBlank
    @JsonView(AgeView.class)
    private Integer age;
    @NotBlank
    @JsonUnwrapped
    @JsonView(ChildView.class)
    private Person child;
    @NotBlank
    @JsonView(FamilyView.class)
    private List<Person> family;
}
