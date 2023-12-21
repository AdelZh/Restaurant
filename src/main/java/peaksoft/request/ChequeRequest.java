package peaksoft.request;

import peaksoft.entity.MenuItem;

import java.util.List;

public record ChequeRequest(

        List<String> menuName,
        String firstName,
        double averagePrice


) {
}
