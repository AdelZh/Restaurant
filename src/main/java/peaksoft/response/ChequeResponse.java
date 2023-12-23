package peaksoft.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.entity.MenuItem;

import java.util.List;


@Builder
@Getter
@Setter
public class ChequeResponse  {

        private String firstName;
        private List<MenuItem> menuName;
        private int averagePrice;
        private int service;
        private int grandTotal;


        public ChequeResponse(String firstName, List<MenuItem> menuName, int averagePrice, int service, int grandTotal) {
                this.firstName = firstName;
                this.menuName = menuName;
                this.averagePrice = averagePrice;
                this.service = service;
                this.grandTotal = grandTotal;
        }
}
