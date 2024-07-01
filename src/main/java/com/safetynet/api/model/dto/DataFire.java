package com.safetynet.api.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;

@Builder
@Data
@JsonPropertyOrder(alphabetic = true)
public class DataFire {
    public interface AgeView {
    }

    public interface FireStationView extends FireStation.StationView {
    }

    public interface InternalView {
    }

    public interface MedicalRecordView extends MedicalRecord.URLsFireView {
    }

    public interface PersonView extends Person.URLsFireView {
    }

    public interface URLsControllerView extends AgeView, FireStationView, InternalView, MedicalRecordView, PersonView {
    }

    @Builder
    @Data
    @JsonPropertyOrder(alphabetic = true)
    public static class DataFirePersonWithRecord {
        @NotNull
        @JsonUnwrapped
        @JsonView(MedicalRecordView.class)
        private MedicalRecord medicalRecord;
        @NotNull
        @JsonUnwrapped
        @JsonView(PersonView.class)
        private Person person;
    }

    @NotNull
    @JsonView(AgeView.class)
    private Integer age;
    @NotNull
    @JsonUnwrapped
    @JsonView(FireStationView.class)
    private FireStation fireStation;
    @JsonView(InternalView.class)
    private DataFirePersonWithRecord persons;
}
