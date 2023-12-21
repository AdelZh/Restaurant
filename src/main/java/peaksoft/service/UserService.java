package peaksoft.service;

import peaksoft.request.AssignWorkerRequest;
import peaksoft.request.SignUpRequest;
import peaksoft.response.SimpleResponse;

public interface UserService {

    SimpleResponse saveUser(SignUpRequest signUpRequest);

    SimpleResponse assign(String acceptOrReject, AssignWorkerRequest assignWorkerRequest);

}
