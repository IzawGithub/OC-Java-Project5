package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonPropertyOrder(alphabetic = true)
public class MedicalRecord {
    public interface FirstNameView {
    }

    public interface LastNameView {
    }

    public interface BirthdateView {
    }

    public interface MedicalView {
    }

    public interface AllergiesView {
    }

    public interface URLsFireView extends MedicalView, AllergiesView {
    }

    public interface URLsFloodView extends MedicalView, AllergiesView {
    }

    public interface URLsPersonInfo extends MedicalView, AllergiesView {
    }

    public interface DefaultView extends FirstNameView, LastNameView, BirthdateView, MedicalView, AllergiesView {
    }

    @EqualsAndHashCode.Include
    @ToString.Include
    @NotBlank
    @JsonView(FirstNameView.class)
    private String firstName;
    @EqualsAndHashCode.Include
    @ToString.Include
    @NotBlank
    @JsonView(LastNameView.class)
    private String lastName;
    @NotBlank
    @Pattern(regexp = "(?:\\d{2}\\/){2}\\d{4}")
    @JsonView(BirthdateView.class)
    private String birthdate;
    @Pattern(regexp = "\\w*:\\d*mg")
    @JsonView(MedicalView.class)
    private String[] medications;
    @JsonView(AllergiesView.class)
    private String[] allergies;
}
