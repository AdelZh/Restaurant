package peaksoft.service;

import peaksoft.request.SignInRequest;
import peaksoft.request.SignUpRequest;
import peaksoft.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse signUp(SignUpRequest request);

    AuthenticationResponse singIn(SignInRequest request);
}
