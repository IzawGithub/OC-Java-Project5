package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonPropertyOrder(alphabetic = true)
public class Person {
    public interface FirstNameView {
    }

    public interface LastNameView {
    }

    public interface AddressView {
    }

    public interface CityView {
    }

    public interface ZipView {
    }

    public interface PhoneView {
    }

    public interface EmailView {
    }

    public interface URLsServedByFireStationView extends FirstNameView, LastNameView, AddressView, PhoneView {
    }

    public interface URLsChildAlertView extends FirstNameView, LastNameView {
    }

    public interface URLsFireView extends FirstNameView, PhoneView {
    }

    public interface URLsFloodView extends LastNameView, PhoneView {
    }

    public interface URLsPersonInfo extends LastNameView, AddressView, EmailView {
    }

    public interface DefaultView
            extends FirstNameView, LastNameView, AddressView, CityView, ZipView, PhoneView, EmailView {
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
    @JsonView(AddressView.class)
    private String address;
    @NotBlank
    @JsonView(CityView.class)
    private String city;
    @NotBlank
    @JsonView(ZipView.class)
    private String zip;
    @NotBlank
    @Pattern(regexp = "(?:\\d{3}-){2}\\d{4}")
    @JsonView(PhoneView.class)
    private String phone;
    @NotBlank
    @Email
    @JsonView(EmailView.class)
    private String email;
}
