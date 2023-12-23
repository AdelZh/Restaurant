package peaksoft.service;

import peaksoft.request.SignInRequest;
import peaksoft.response.AuthenticationResponse;

public interface AuthenticationService {


    AuthenticationResponse singIn(SignInRequest request);
}
