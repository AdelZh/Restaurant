package peaksoft.request;

import jakarta.validation.constraints.NotNull;
import peaksoft.enums.Role;
import peaksoft.valid.validation.EmailValidation;
import peaksoft.valid.validation.PasswordValidation;
import peaksoft.valid.validation.PhoneNumberValidation;

import java.time.LocalDate;

public record SignUpRequest(

        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @EmailValidation
        String email,
        @PasswordValidation
        String password,
        @PhoneNumberValidation
        String phoneNumber,
        @NotNull
        LocalDate experience,
        @NotNull
        LocalDate dateOfBirth,
        Role role



) {


}
