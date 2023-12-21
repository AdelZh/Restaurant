package peaksoft.request;

import jakarta.validation.constraints.NotNull;

public record SignInRequest(

        String email,
        String password
) {

}
