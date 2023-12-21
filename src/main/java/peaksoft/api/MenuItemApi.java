package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.MenuItem;
import peaksoft.request.MenuRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.MenuItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
@EnableMethodSecurity
public class MenuItemApi {

    private final MenuItemService menuItemService;


    @PostMapping
    public SimpleResponse save(@RequestBody MenuRequest menuRequest){
        return menuItemService.saveMenu(menuRequest);
    }



    @GetMapping
    public ResponseEntity<List<MenuItem>> getAll(@RequestParam String search){
        List<MenuItem> menuItems=menuItemService.getAll(search);
        return ResponseEntity.ok(menuItems);
    }


    @GetMapping("sort")
    public ResponseEntity<List<MenuItem>> sortByPrice(@RequestParam String ascOrDesc){
        List<MenuItem> menuItems=menuItemService.sortByPrice(ascOrDesc);
        return ResponseEntity.ok(menuItems);
    }


    @GetMapping("/filter")
    public ResponseEntity<List<MenuItem>> filter(@RequestParam Boolean filter){
        List<MenuItem> menuItems=menuItemService.filterByIsVegetarian(filter);
        return ResponseEntity.ok(menuItems);
    }
}
