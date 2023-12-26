package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import peaksoft.request.AssignWorkerRequest;
import peaksoft.request.SignUpRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.UserService;

@RestController
@Controller
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {

    private final UserService userService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/assign")
    public SimpleResponse assign(@RequestParam String acceptOrReject, @RequestBody AssignWorkerRequest assignWorkerRequest){
        return userService.assign(acceptOrReject, assignWorkerRequest);
    }


    @PutMapping
    public SimpleResponse updateUser(@RequestBody @Valid SignUpRequest signUpRequest, @RequestParam Long id) {
        return userService.updateUser(id, signUpRequest);
    }


    @DeleteMapping
    public SimpleResponse delete(@RequestBody SignUpRequest signUpRequest){
        return userService.delete(signUpRequest);
    }

}
