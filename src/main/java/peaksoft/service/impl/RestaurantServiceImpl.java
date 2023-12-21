package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.MenuItemRepo;
import peaksoft.repo.RestaurantRepo;
import peaksoft.request.RestaurantRequest;
import peaksoft.request.RestoRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.RestaurantService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepo restaurantRepo;
    private final MenuItemRepo menuItemRepo;


    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest request) {
        Restaurant restaurant = Restaurant.builder()
                .name(request.name())
                .location(request.location())
                .restType(request.restType())
                .build();

        restaurantRepo.save(restaurant);
        return new SimpleResponse(HttpStatus.OK,
                "restaurant with name: " + request.name() + " successfully saved!");
    }


    @Override
    public Long countCheque(RestoRequest request) {
        LocalDate today=LocalDate.now();
        List<MenuItem> menuItems=menuItemRepo.getAll3(request.name());
        long count=0L;

        for (MenuItem menuItem1:menuItems){
            List<Cheque> cheques=menuItem1.getCheque();
            for(Cheque cheque:cheques){
                ZonedDateTime chequeDate=cheque.getCreateAt();
                if (chequeDate != null && chequeDate.toLocalDate().isEqual(today)){
                    count++;
                }
            }
        }
       return count;
    }


    @Override
    public SimpleResponse delete(RestaurantRequest request) {
        Restaurant restaurant=restaurantRepo.getRestaurantByName(request.name()).orElseThrow(
                () -> {
                    String message="restaurant with given name: "+request.name()+" not found";
                    log.error(message);
                    return new NotFoundException(message);
                }
        );

       for (MenuItem menuItem:menuItemRepo.findAll()){
            restaurant.getMenuItem().remove(menuItem);
            menuItemRepo.delete(menuItem);
        }




        restaurantRepo.delete(restaurant);
        return new SimpleResponse(HttpStatus.OK, "deleted");

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