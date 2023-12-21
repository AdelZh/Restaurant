package peaksoft.service;

import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.request.CategoryRequest;
import peaksoft.response.SimpleResponse;

import java.util.List;

public interface CategoryService {

    SimpleResponse saveCategory(CategoryRequest categoryRequest);

    List<Category> getBySearch(String search);

    SimpleResponse delete(CategoryRequest categoryRequest);
}
