package peaksoft.request;

import peaksoft.enums.Role;

import java.time.LocalDate;

public record SignUpRequest(
        String firstName,
        String lastName,

        String email,

        String password,

        String phoneNumber,
        LocalDate experience,
        LocalDate dateOfBirth,
        Role role
) {


}
