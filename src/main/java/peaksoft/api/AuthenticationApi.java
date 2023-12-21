package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.request.SignInRequest;
import peaksoft.request.SignUpRequest;
import peaksoft.response.AuthenticationResponse;
import peaksoft.service.AuthenticationService;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationApi {

    private final AuthenticationService authenticationService;

    @PermitAll
    @PostMapping("/signUp")
    AuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return authenticationService.signUp(signUpRequest);
    }


    @PermitAll
    @PostMapping("/signIn")
    AuthenticationResponse sigIn(@RequestBody SignInRequest signInRequest){
        return authenticationService.singIn(signInRequest);
    }
}
