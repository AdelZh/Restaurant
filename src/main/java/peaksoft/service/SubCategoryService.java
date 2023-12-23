package peaksoft.service;

import peaksoft.entity.SubCategory;
import peaksoft.request.CategoryRequest;
import peaksoft.request.SubCategoryRequest;
import peaksoft.response.SimpleResponse;

import java.util.List;

public interface SubCategoryService {


    SimpleResponse save(SubCategoryRequest subCategoryRequest);

    List<SubCategory> getAllByCategoryName(CategoryRequest categoryRequest);

    List<SubCategory> getAll(String categoryName);

    List<SubCategory> getBySearch(String search);
    SimpleResponse delete(Long id);

    SimpleResponse update(Long id,SubCategoryRequest subCategoryRequest);
}
