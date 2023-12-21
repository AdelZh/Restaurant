package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import peaksoft.request.AssignWorkerRequest;
import peaksoft.request.SignUpRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.UserService;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {

    private final UserService userService;


    @PostMapping
    public SimpleResponse save(@RequestBody SignUpRequest signUpRequest){
        return userService.saveUser(signUpRequest);
    }



    @PostMapping("/assign")
    public SimpleResponse assign(@RequestParam String acceptOrReject, @RequestBody AssignWorkerRequest assignWorkerRequest){
        return userService.assign(acceptOrReject, assignWorkerRequest);
    }



}
