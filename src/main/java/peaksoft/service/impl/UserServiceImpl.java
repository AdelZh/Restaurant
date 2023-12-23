package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.entity.Cheque;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.BadCredentialsException;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.RestaurantRepo;
import peaksoft.repo.UserRepo;
import peaksoft.request.AssignWorkerRequest;
import peaksoft.request.SignUpRequest;
import peaksoft.response.AuthenticationResponse;
import peaksoft.response.SimpleResponse;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final RestaurantRepo restaurantRepo;
    private final UserRepo userRepo;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse saveUser(SignUpRequest signUpRequest) {
        if (userRepo.existsByEmail(signUpRequest.email())) {
            String message = "user with email: " + signUpRequest.email() + " already exist";
            log.error(message);
            throw new AlreadyExistException(message);

        }
        User user = User.builder()
                .firstName(signUpRequest.firstName())
                .lastName(signUpRequest.lastName())
                .email(signUpRequest.email())
                .password(signUpRequest.password())
                .phoneNumber(signUpRequest.phoneNumber())
                .dateOfBirth(signUpRequest.dateOfBirth())
                .experience(signUpRequest.experience())
                .role(signUpRequest.role())
                .build();

        LocalDate dateOfBirth = signUpRequest.dateOfBirth();
        LocalDate currentZoneDate = LocalDate.now();

        int experience = Period.between(signUpRequest.experience(), LocalDate.now()).getYears();


        int age = Period.between(dateOfBirth, currentZoneDate).getYears();
        if (signUpRequest.role() == Role.WAITER) {
            if (age < 18 || age > 30) {
                throw new BadCredentialsException("not valid age for company");
            }
            if (experience < 1) {
                throw new BadCredentialsException("The candidate's work experience is low!");
            }
        } else if (signUpRequest.role() == Role.CHEF) {
            if (age < 25 || age > 45) {
                throw new BadCredentialsException("not valid age for company");
            }
            if (experience < 2) {
                throw new BadCredentialsException("The candidate's work experience is low!");
            }
        }
        userRepo.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().
                token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }





    @Override
    public SimpleResponse assign(String acceptOrReject, AssignWorkerRequest assignWorkerRequest) {
        Restaurant restaurant = restaurantRepo.getRestaurantById(assignWorkerRequest.restaurantId()).orElseThrow(
                () -> {
                    String message = "such restaurant with id: " + assignWorkerRequest.restaurantId() + " not founded";
                    log.error(message);
                    return new NotFoundException(message);
                }
        );
        List<String> users = assignWorkerRequest.email();
        List<User> notAssignedUser = userRepo.findByUsername(users);

        if (userRepo.countUserByRestaurant(assignWorkerRequest.restaurantId()) > 8) {
            throw new BadCredentialsException("no vacancy");
        } else {
            if (!notAssignedUser.isEmpty() && acceptOrReject.equalsIgnoreCase("Accept")) {
                for (User user : notAssignedUser) {
                    user.setRestaurant(restaurant);
                    userRepo.save(user);
                    return new SimpleResponse(HttpStatus.OK, "assigned");
                }
            } else if (!notAssignedUser.isEmpty() && acceptOrReject.equalsIgnoreCase("Reject")) {
                userRepo.deleteAll(notAssignedUser);
                return new SimpleResponse(HttpStatus.OK, "users deleted");
            }
            return new SimpleResponse(HttpStatus.OK, "users not found");
        }
    }


    @Override
    public SimpleResponse updateUser(Long id, SignUpRequest signUpRequest) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("User with id:%s not found", id)
                )
        );
        user.setFirstName(signUpRequest.firstName());
        user.setLastName(signUpRequest.lastName());
        user.setDateOfBirth(signUpRequest.dateOfBirth());
        user.setEmail(signUpRequest.email());
        user.setPassword(signUpRequest.password());
        user.setPhoneNumber(signUpRequest.firstName());
        user.setExperience(signUpRequest.experience());
        user.setRole(signUpRequest.role());
        userRepo.save(user);

        return new SimpleResponse(HttpStatus.OK, "updated");
    }


    @Override
    public SimpleResponse delete(SignUpRequest signUpRequest) {
        List<User> users=userRepo.getUserByEmail(signUpRequest.email()).stream().toList();

        if(users.isEmpty()){
            throw new NotFoundException("user with email is: " +signUpRequest.email()+ " not found");
        }
        for(User user:users){
            for (Cheque cheque:user.getCheque()){
                cheque.setUser(null);
            }
            userRepo.delete(user);
        }
        return new SimpleResponse(HttpStatus.OK, "deleted");
    }
}


