package peaksoft.api;

import jakarta.validation.Valid;
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
import peaksoft.service.UserService;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationApi {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/signUp")
    AuthenticationResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest){
        return userService.saveUser(signUpRequest);
    }


    @PostMapping("/signIn")
    AuthenticationResponse sigIn(@RequestBody SignInRequest signInRequest){
        return authenticationService.singIn(signInRequest);
    }
}
