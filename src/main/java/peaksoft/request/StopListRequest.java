package peaksoft.request;

import java.time.LocalDate;

public record StopListRequest(

        LocalDate date,
        String reason,
        String menuName
) {
}
