package com.safetynet.api.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.api.model.Person;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonPropertyOrder(alphabetic = true)
public class DataServedByFireStation {
    public interface NumberAdultsView {
    }

    public interface NumberChildsView {
    }

    public interface PersonsView extends Person.URLsServedByFireStationView {
    }

    public interface URLsControllerView extends NumberAdultsView, NumberChildsView, PersonsView {
    }

    @NotBlank
    @JsonView(NumberAdultsView.class)
    private Integer numberAdults;
    @NotBlank
    @JsonView(NumberChildsView.class)
    private Integer numberChilds;
    @JsonView(PersonsView.class)
    private List<Person> persons;
}
