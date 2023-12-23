package peaksoft.service;

import peaksoft.request.RestaurantRequest;
import peaksoft.request.RestoRequest;
import peaksoft.response.SimpleResponse;

public interface RestaurantService {

    SimpleResponse saveRestaurant(RestaurantRequest request);
    Long countCheque(RestoRequest request);
    SimpleResponse delete(RestaurantRequest request);

    SimpleResponse update(Long id, RestaurantRequest request);


}
