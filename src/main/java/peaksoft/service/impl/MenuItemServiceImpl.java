package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.*;
import peaksoft.exception.BadCredentialsException;
import peaksoft.exception.NegativeNumberException;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.MenuItemRepo;
import peaksoft.repo.RestaurantRepo;
import peaksoft.repo.StopListRepo;
import peaksoft.repo.SubCategoryRepo;
import peaksoft.request.MenuRequest;
import peaksoft.response.PaginationResponse;
import peaksoft.response.SimpleResponse;
import peaksoft.service.MenuItemService;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

    private final RestaurantRepo restaurantRepo;
    private final MenuItemRepo menuItemRepo;
    private final SubCategoryRepo subCategory;
    private final StopListRepo stopListRepo;


    @Override
    public SimpleResponse saveMenu(MenuRequest menuRequest) {
        Restaurant restaurant=restaurantRepo.getRestaurantByName(menuRequest.name()).orElseThrow(
                () -> {
                    String message="restaurant with such name: "+menuRequest.name()+"not found";
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
                .name(menuRequest.menuName())
                .image(menuRequest.image())
                .price(menuRequest.price())
                .description(menuRequest.description())
                .isVegetarian(menuRequest.isVegetarian())
                .build();

        menuItem.setRestaurant(restaurant);
        menuItem.setSubCategory(subCategory1);
        menuItemRepo.save(menuItem);

        return new SimpleResponse(HttpStatus.OK, "saved");
    }



    @Override
    public SimpleResponse delete(Long id) {
        List<MenuItem> menuItem=menuItemRepo.getMenuItemsById(id).stream().toList();

        if(menuItem.isEmpty()){
            throw new NotFoundException("menu with ID: "+id+" not found");
        }
        for(MenuItem menuItem1:menuItem){
            Iterator<Cheque> iterator=menuItem1.getCheque().iterator();
            while (iterator.hasNext()){
                Cheque cheque=iterator.next();
                cheque.setMenuItem(null);
                iterator.remove();

            }
            StopList stopList = menuItem1.getStopList();
            if (stopList != null) {
                menuItem1.setStopList(null);
                stopListRepo.delete(stopList);
            }

            Restaurant restaurant=menuItem1.getRestaurant();
            if(restaurant != null){
              menuItem1.setRestaurant(null);
            }
            menuItemRepo.delete(menuItem1);
        }
        return new SimpleResponse(HttpStatus.OK, "deleted");

    }


    @Override
    public PaginationResponse paginationGetAll(int page, int size) {
        Pageable pageable= PageRequest.of(page-1, size);
        Page<MenuItem> menu=menuItemRepo.getMenuItems(pageable);
        return PaginationResponse.builder()
                .menuItems(menu.getContent())
                .page(menu.getNumber()+1)
                .size(menu.getSize())
                .build();
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
    public SimpleResponse update(Long id, MenuRequest menuRequest) {
        MenuItem menuItem=menuItemRepo.getMenuItemsById(id).orElseThrow(
                () -> {
                    String message="menu with id: "+id+" is not found";
                    log.error(message);
                    return new NotFoundException(message);
                }
        );

            menuItem.setName(menuRequest.menuName());
            menuItem.setImage(menuRequest.image());
            menuItem.setPrice(menuRequest.price());
            menuItem.setIsVegetarian(menuRequest.isVegetarian());
            menuItem.setDescription(menuRequest.description());

            menuItemRepo.save(menuItem);

        return new SimpleResponse(HttpStatus.OK, "updated");
    }

    @Override
    public List<MenuItem> filterByIsVegetarian(Boolean filter) {
        return menuItemRepo.filter(filter);
    }
}
