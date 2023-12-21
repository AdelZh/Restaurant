package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.SubCategory;
import peaksoft.request.CategoryRequest;
import peaksoft.request.SubCategoryRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.SubCategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subCategory")
@EnableMethodSecurity
public class SubCategoryApi {

    private final SubCategoryService service;

    @PostMapping
    public SimpleResponse save(@RequestBody SubCategoryRequest subCategoryRequest) {
        return service.save(subCategoryRequest);
    }


    @GetMapping
    public ResponseEntity<List<SubCategory>> getAll(@RequestBody CategoryRequest categoryRequest) {
        List<SubCategory> subCategories = service.getAllByCategoryName(categoryRequest);
        return ResponseEntity.ok(subCategories);
    }


    @GetMapping("getAll")
    public ResponseEntity<List<SubCategory>> getAllByGrouping() {
        List<SubCategory> subCategories = service.getAll();
        return ResponseEntity.ok(subCategories);
    }

    @GetMapping("search")
    public ResponseEntity<List<SubCategory>> getAllBySearch(@RequestParam String search){
        List<SubCategory> subCategories = service.getBySearch(search);
        return ResponseEntity.ok(subCategories);
    }

}

