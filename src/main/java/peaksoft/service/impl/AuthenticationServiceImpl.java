package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.entity.User;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.UserRepo;
import peaksoft.request.SignInRequest;
import peaksoft.response.AuthenticationResponse;
import peaksoft.service.AuthenticationService;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;




   /*@PostConstruct
    public void init(){
        User user = User.builder()
                .firstName("admin")
                .lastName("diane")
                .dateOfBirth(LocalDate.now())
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("diane"))
                .phoneNumber("+996507658392")
                .role(Role.ADMIN)
                .experience(LocalDate.now())
                .build();
        if(userRepo.existsByEmail(user.getEmail())){
            log.error("user with email: " +user.getEmail()+ " already exist");
            throw new EntityExistsException("user with email: " +user.getEmail()+ " already exist");
        }else {
            userRepo.save(user);
        }
    }

    */






        @Override
        public AuthenticationResponse singIn(SignInRequest request) {
        User user = userRepo.getUserByEmail(request.email()).orElseThrow(
                () -> {
                    log.error( "user with email: " + request.email() + " not found");
                    return new NotFoundException(
                            "user with email: " + request.email() + " not found");
                });

        if (request.email().isBlank()) {
            throw  new BadCredentialsException("email is blank");
        }
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("wrong password");
        }
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().
                token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}