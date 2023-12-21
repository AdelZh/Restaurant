package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.Category;
import peaksoft.repo.CategoryRepo;
import peaksoft.repo.SubCategoryRepo;
import peaksoft.request.CategoryRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final SubCategoryRepo subCategoryRepo;


    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.categoryName())
                .build();
        categoryRepo.save(category);

        return new SimpleResponse(HttpStatus.OK, "saved");
    }


    @Override
    public SimpleResponse delete(CategoryRequest categoryRequest) {
       return null;
    }


    @Override
    public List<Category> getBySearch(String search) {
        return categoryRepo.getBySearch(search);
    }
}

