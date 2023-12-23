package peaksoft.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.entity.MenuItem;

import java.util.List;

@Builder
@Getter
@Setter
public class PaginationResponse{

        private List<MenuItem> menuItems;
        private int page;
        private int size;


    public PaginationResponse(List<MenuItem> menuItems, int page, int size) {
        this.menuItems = menuItems;
        this.page = page;
        this.size = size;
    }
}
