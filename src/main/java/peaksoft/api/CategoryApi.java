package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.request.CategoryRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@EnableMethodSecurity
public class CategoryApi {


    private final CategoryService categoryService;


    @PostMapping
    public SimpleResponse save(@RequestBody CategoryRequest categoryRequest){
        return categoryService.saveCategory(categoryRequest);
    }



    @GetMapping()
    public ResponseEntity<List<Category>> getAllBySearch(@RequestParam String search){
        List<Category> categories = categoryService.getBySearch(search);
        return ResponseEntity.ok(categories);
    }

}
