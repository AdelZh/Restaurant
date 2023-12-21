package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.CategoryRepo;
import peaksoft.repo.SubCategoryRepo;
import peaksoft.request.CategoryRequest;
import peaksoft.request.SubCategoryRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.SubCategoryService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService {


    private final CategoryRepo categoryRepo;
    private final SubCategoryRepo subCategoryRepo;

    @Override
    public SimpleResponse save(SubCategoryRequest subCategoryRequest) {
        Category category=categoryRepo.getCategoriesByName(subCategoryRequest.categoryName()).orElseThrow(
                () -> {
                    String message="such category not founded with name: "+subCategoryRequest.categoryName();
                    log.error(message);
                    return new NotFoundException(message);
                }
        );

        SubCategory subCategory=SubCategory.builder()
                .name(subCategoryRequest.subCategoryName())
                .build();
        subCategory.setCategory(category);
        subCategoryRepo.save(subCategory);
        return new SimpleResponse(HttpStatus.OK, "saved");
    }


    @Override
    public List<SubCategory> getAllByCategoryName(CategoryRequest categoryRequest) {
       return subCategoryRepo.getSubCategoriesByCategoryName(categoryRequest.categoryName());
    }


    @Override
    public List<SubCategory> getAll() {
        return subCategoryRepo.getAll();
    }


    @Override
    public List<SubCategory> getBySearch(String search) {
        return subCategoryRepo.getBySearch(search);
    }
}
