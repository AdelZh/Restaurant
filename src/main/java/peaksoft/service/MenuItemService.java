package peaksoft.service;

import peaksoft.entity.MenuItem;
import peaksoft.request.MenuRequest;
import peaksoft.response.PaginationResponse;
import peaksoft.response.SimpleResponse;

import java.util.List;

public interface MenuItemService {


    SimpleResponse saveMenu(MenuRequest menuRequest);

    List<MenuItem> getAll(String search);

    List<MenuItem> sortByPrice(String ascOrDesc);

    List<MenuItem> filterByIsVegetarian(Boolean filter);

    SimpleResponse delete(Long id);
    SimpleResponse update(Long id, MenuRequest menuRequest);

    PaginationResponse paginationGetAll(int page, int size);
}
