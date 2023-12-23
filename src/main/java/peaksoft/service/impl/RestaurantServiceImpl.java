package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.*;
import peaksoft.exception.BadCredentialsException;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.*;
import peaksoft.request.RestaurantRequest;
import peaksoft.request.RestoRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.RestaurantService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepo restaurantRepo;
    private final MenuItemRepo menuItemRepo;
    private final UserRepo userRepo;
    private final StopListRepo stopListRepo;
    private final ChequeRepo chequeRepo;

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest request) {
        Restaurant restaurant = Restaurant.builder()
                .name(request.name())
                .location(request.location())
                .restType(request.restType())
                .numberOfEmployees(request.numberOfEmployees())
                .build();

        restaurantRepo.save(restaurant);
        return new SimpleResponse(HttpStatus.OK,
                "restaurant with name: " + request.name() + " successfully saved!");
    }


    @Override
    public Long countCheque(RestoRequest request) {
        LocalDate today = LocalDate.now();
        List<MenuItem> menuItems = menuItemRepo.getAll3(request.name());
        long count = 0L;

        for (MenuItem menuItem1 : menuItems) {
            if(menuItem1.getCheque().isEmpty()){
                throw new BadCredentialsException("this foods was not serve today: "+request.name());
            }
            List<Cheque> cheques = menuItem1.getCheque();
            for (Cheque cheque : cheques) {
                ZonedDateTime chequeDate = cheque.getCreateAt();
                if (chequeDate != null && chequeDate.toLocalDate().isEqual(today)) {
                    count++;
                }
            }
        }
        return count;
    }


    @Override
    public SimpleResponse delete(RestaurantRequest request) {
        Restaurant restaurant = restaurantRepo.getRestaurantByName(request.name())
                .orElseThrow(() -> {
                    String message = "restaurant with given name: " + request.name() + " not found";
                    log.error(message);
                    return new NotFoundException(message);
                });

        List<MenuItem> menuItems = new ArrayList<>(restaurant.getMenuItem());
        for (MenuItem menuItem : menuItems) {
            StopList stopList = menuItem.getStopList();
            if (stopList != null) {
                menuItem.setStopList(null);
                stopListRepo.delete(stopList);
            }

            restaurant.getMenuItem().remove(menuItem);
            menuItem.setRestaurant(null);
            menuItemRepo.delete(menuItem);
        }

        List<User> users = restaurant.getUser();
        for (User user : users) {
            Iterator<Cheque> iterator=user.getCheque().iterator();
            while (iterator.hasNext()){
                Cheque cheque=iterator.next();
                cheque.setUser(null);
                iterator.remove();
                chequeRepo.delete(cheque);
            }
            user.setRestaurant(null);
            userRepo.delete(user);
        }
        restaurantRepo.delete(restaurant);
        return new SimpleResponse(HttpStatus.OK, "deleted");
    }




    @Override
    public SimpleResponse update(Long id, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepo.getRestaurantById(id)
                .orElseThrow(() -> {
                    String message = "restaurant with given name: " + id + " not found";
                    log.error(message);
                    return new NotFoundException(message);
                });
        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setNumberOfEmployees(request.numberOfEmployees());
        restaurant.setRestType(request.restType());

        restaurantRepo.save(restaurant);

        return new SimpleResponse(HttpStatus.OK, "updated");
    }
}



























































































  /*ZonedDateTime today=ZonedDateTime.now();
        List<Restaurant> restaurants=restaurantRepo.getRestaurantByName(request.name()).stream().toList();

        long count=0L;

        for(Restaurant restaurant:restaurants){
            List<MenuItem> menuItem=restaurant.getMenuItem();
           for (MenuItem menuItem1:menuItem){
               List<Cheque> cheques=menuItem1.getCheque();
               for(Cheque cheque:cheques){
                   ZonedDateTime chequeDate=cheque.getCreateAt();
                   if (chequeDate != null && chequeDate.equals(today)){
                       count++;

                   }
               }
           }
        }
        return count;
    }

       */