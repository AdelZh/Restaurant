package peaksoft.request;

import peaksoft.entity.User;

import java.util.List;

public record AssignWorkerRequest(

        Long restaurantId,
        List<String> email
) {
}
