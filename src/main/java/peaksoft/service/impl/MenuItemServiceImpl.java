package peaksoft.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.SubCategory;
import peaksoft.exception.BadCredentialsException;
import peaksoft.exception.NegativeNumberException;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.MenuItemRepo;
import peaksoft.repo.RestaurantRepo;
import peaksoft.repo.SubCategoryRepo;
import peaksoft.request.MenuRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.MenuItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

    private final RestaurantRepo restaurantRepo;
    private final MenuItemRepo menuItemRepo;
    private final SubCategoryRepo subCategory;

    @Override
    public SimpleResponse saveMenu(MenuRequest menuRequest) {
        Restaurant restaurant=restaurantRepo.getRestaurantByName(menuRequest.restaurantName()).orElseThrow(
                () -> {
                    String message="restaurant with such name: "+menuRequest.restaurantName()+"not found";
                    log.error(message);
                    return new NotFoundException(message);
                }
        );

        SubCategory subCategory1=subCategory.getSubCategoriesByName(menuRequest.subCategoryName()).orElseThrow(
                () -> {
                    String message="subCategory with such name: "+menuRequest.subCategoryName()+"not found";
                    log.error(message);
                    return new NotFoundException(message);
                }
        );

        if(menuRequest.price()<0){
            throw new NegativeNumberException("price must be positive");
        }

        MenuItem menuItem=MenuItem.builder()
                .name(menuRequest.name())
                .image(menuRequest.image())
                .price(menuRequest.price())
                .description(menuRequest.description())
                .isVegetarian(menuRequest.isVegetarian())
                .build();

        menuItem.setRestaurant(restaurant);
        menuItem.setSubCategories(subCategory1);
        menuItemRepo.save(menuItem);

        return new SimpleResponse(HttpStatus.OK, "saved");
    }




    @Override
    public List<MenuItem> getAll(String search) {
        return menuItemRepo.getAll2(search);
    }


    @Override
    public List<MenuItem> sortByPrice(String ascOrDesc) {
        if (ascOrDesc.equalsIgnoreCase("asc")){
            return menuItemRepo.sortByPriceAsc();
        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
            return menuItemRepo.sortByPriceDesc();
        }
        throw new BadCredentialsException("write correct input!!");
    }


    @Override
    public List<MenuItem> filterByIsVegetarian(Boolean filter) {
        return menuItemRepo.filter(filter);
    }
}
