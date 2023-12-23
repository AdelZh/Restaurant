package peaksoft.request;

import java.util.List;

public record ChequeRequest(

        List<String> menuName,
        String email,
        double averagePrice


) {
}
