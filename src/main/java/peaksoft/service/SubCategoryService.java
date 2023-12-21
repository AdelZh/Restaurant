package peaksoft.service;

import peaksoft.entity.MenuItem;
import peaksoft.entity.SubCategory;
import peaksoft.request.CategoryRequest;
import peaksoft.request.SubCategoryRequest;
import peaksoft.response.SimpleResponse;

import java.util.List;

public interface SubCategoryService {


    SimpleResponse save(SubCategoryRequest subCategoryRequest);

    List<SubCategory> getAllByCategoryName(CategoryRequest categoryRequest);

    List<SubCategory> getAll();

    List<SubCategory> getBySearch(String search);
}
