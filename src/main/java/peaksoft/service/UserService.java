package peaksoft.service;

import peaksoft.request.AssignWorkerRequest;
import peaksoft.request.SignUpRequest;
import peaksoft.response.AuthenticationResponse;
import peaksoft.response.SimpleResponse;

public interface UserService {


    SimpleResponse updateUser(Long userId, SignUpRequest signUpRequest);
    AuthenticationResponse saveUser(SignUpRequest signUpRequest);

    SimpleResponse assign(String acceptOrReject, AssignWorkerRequest assignWorkerRequest);

    SimpleResponse delete(SignUpRequest signUpRequest);

}
