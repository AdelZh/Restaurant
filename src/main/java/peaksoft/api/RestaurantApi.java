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


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<Long> count(@RequestBody RestoRequest request){
        Long count=restaurantService.countCheque(request);
        return ResponseEntity.ok(count);
    }

    @PutMapping
    public SimpleResponse update(@RequestParam Long id, @RequestBody RestaurantRequest request){
        return restaurantService.update(id, request);
    }

    @DeleteMapping
    public SimpleResponse delete(@RequestBody RestaurantRequest request){
       return restaurantService.delete(request);
    }
}
