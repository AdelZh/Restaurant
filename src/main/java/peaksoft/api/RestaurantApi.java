package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import peaksoft.request.RestaurantRequest;
import peaksoft.request.RestoRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.RestaurantService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
@EnableMethodSecurity
public class RestaurantApi {

    private final RestaurantService restaurantService;


    @PostMapping
    public SimpleResponse save(@RequestBody RestaurantRequest request){
        return restaurantService.saveRestaurant(request);
    }


    @GetMapping
    public ResponseEntity<Long> count(@RequestBody RestoRequest request){
        Long count=restaurantService.countCheque(request);
        return ResponseEntity.ok(count);
    }


    @DeleteMapping
    public SimpleResponse delete(@RequestBody RestaurantRequest request){
       return restaurantService.delete(request);
    }
}
