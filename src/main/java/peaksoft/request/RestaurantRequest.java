package peaksoft.request;


import jakarta.validation.constraints.NotNull;
import peaksoft.enums.RestType;

public record RestaurantRequest(

        @NotNull
        String name,
        @NotNull
        String location,
        RestType restType

) {
}
