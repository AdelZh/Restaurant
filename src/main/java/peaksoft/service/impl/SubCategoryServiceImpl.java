package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.Category;
import peaksoft.entity.MenuItem;
import peaksoft.entity.SubCategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.CategoryRepo;
import peaksoft.repo.SubCategoryRepo;
import peaksoft.request.CategoryRequest;
import peaksoft.request.SubCategoryRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.SubCategoryService;

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
    public List<SubCategory> getAll(String categoryName) {
        List<SubCategory> category=subCategoryRepo.getAll(categoryName);

        if(category.isEmpty()){
            throw new NullPointerException("such category does not exist: "+categoryName);
        }
        return category;
    }


    @Override
    public List<SubCategory> getBySearch(String search) {
        return subCategoryRepo.getBySearch(search);
    }


    @Override
    public SimpleResponse delete(Long id) {
        List<SubCategory> subCategory=subCategoryRepo.getSubCategoriesById(id);

        if(subCategory.isEmpty()){
            throw new NotFoundException("subCategory with ID: "+id+" is not found");
        }

        for(SubCategory subCategory1:subCategory){
            for (MenuItem menuItem : subCategory1.getMenuItems()) {
                menuItem.setSubCategory(null);
            }
            if(subCategory1.getCategory()!=null){
                Category categories=subCategory1.getCategory();
                categories.setSubCategories(null);
            }else {
                subCategoryRepo.delete(subCategory1);
            }
        }
        return new SimpleResponse(HttpStatus.OK, "deleted");
    }


    @Override
    public SimpleResponse update(Long id,SubCategoryRequest subCategoryRequest) {
        List<SubCategory> subCategories=subCategoryRepo.getSubCategoriesById(id);

        if (subCategories.isEmpty()){
            throw new NotFoundException("subCategory is does not exist: "+id);
        }
        for(SubCategory subCategory:subCategories){
            subCategory.setName(subCategoryRequest.subCategoryName());

            subCategoryRepo.save(subCategory);
        }
        return new SimpleResponse(HttpStatus.OK, "updated");
    }
}
