package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.RestaurantRepo;
import peaksoft.repo.UserRepo;
import peaksoft.request.AssignWorkerRequest;
import peaksoft.request.SignUpRequest;
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

    @Override
    public SimpleResponse saveUser(SignUpRequest signUpRequest) {
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
                throw new RuntimeException("not valid age for company");
            }
            if (experience < 1) {
                throw new RuntimeException("The candidate's work experience is low!");
            }
        } else if (signUpRequest.role() == Role.CHEF) {
            if (age < 25 || age > 45) {
                throw new RuntimeException("not valid age for company");
            }
            if (experience < 2) {
                throw new RuntimeException("The candidate's work experience is low!");
            }
        }
        userRepo.save(user);
        return new SimpleResponse(HttpStatus.ACCEPTED, "saved");
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
            throw new RuntimeException("no vacancy");
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
}


