package peaksoft.service;

import peaksoft.request.AssignWorkerRequest;
import peaksoft.request.RestaurantRequest;
import peaksoft.request.RestoRequest;
import peaksoft.response.SimpleResponse;

import java.time.LocalDate;

public interface RestaurantService {

    SimpleResponse saveRestaurant(RestaurantRequest request);
    Long countCheque( RestoRequest request);
    SimpleResponse delete(RestaurantRequest request);


}
