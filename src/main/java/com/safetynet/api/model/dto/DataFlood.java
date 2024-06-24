package com.safetynet.api.model.dto;

import java.util.List;
import java.util.Map;

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
public class DataFlood {
    public interface AddressView {

    }

    public interface AgeView {
    }

    public interface FireStationView extends FireStation.StationView {
    }

    public interface InternalView {
    }

    public interface MedicalRecordView extends MedicalRecord.URLsFloodView {
    }

    public interface PersonView extends Person.URLsFloodView {
    }

    public interface URLsControllerView
            extends AddressView, AgeView, FireStationView, InternalView, MedicalRecordView, PersonView {
    }

    @Builder
    @Data
    @JsonPropertyOrder(alphabetic = true)
    public static class DataFloodPersonWithRecord {
        @NotNull
        @JsonView(AgeView.class)
        private Integer age;
        @NotNull
        @JsonUnwrapped
        @JsonView(MedicalRecordView.class)
        private MedicalRecord medicalRecord;
        @NotNull
        @JsonUnwrapped
        @JsonView(PersonView.class)
        private Person person;
    }

    @JsonUnwrapped
    @JsonView(FireStationView.class)
    private FireStation fireStation;
    @JsonView(InternalView.class)
    private Map<String, List<DataFloodPersonWithRecord>> persons;

}
