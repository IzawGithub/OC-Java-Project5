package com.safetynet.api.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonPropertyOrder(alphabetic = true)
public class DataInfo {
    public interface AgeView {
    }

    public interface PersonView extends Person.URLsPersonInfo {
    }

    public interface MedicalRecordView extends MedicalRecord.URLsPersonInfo {
    }

    public interface URLsControllerView extends AgeView, PersonView, MedicalRecordView {
    }

    @NotNull
    @JsonView(AgeView.class)
    private Integer age;
    @NotNull
    @JsonUnwrapped
    @JsonView(PersonView.class)
    private Person person;
    @NotNull
    @JsonUnwrapped
    @JsonView(MedicalRecordView.class)
    private MedicalRecord medicalRecord;
}
