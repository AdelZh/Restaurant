package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.CategoryRepo;
import peaksoft.request.CategoryRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.CategoryService;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepo categoryRepo;

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
        List<Category> categories=categoryRepo.getCategoriesByName(categoryRequest.categoryName()).stream().toList();

        if(categories.isEmpty()){
            throw new NotFoundException("category with given name: "+categoryRequest.categoryName()+" not found");
        }

        for(Category category:categories){
            Iterator<SubCategory> iterator=category.getSubCategories().iterator();
            while (iterator.hasNext()){
                SubCategory subCategory=iterator.next();
                subCategory.setCategory(null);
                iterator.remove();
            }
            categoryRepo.delete(category);

        }
        return new SimpleResponse(HttpStatus.OK, "deleted");
    }



    @Override
    public SimpleResponse update(Long id, CategoryRequest categoryRequest) {
        Category category=categoryRepo.findById(id).orElseThrow(
                () -> {
                    String message="category with name: " +id+ " is not found";
                    log.error(message);
                    return new NotFoundException(message);
                });
        category.setName(categoryRequest.categoryName());
        categoryRepo.save(category);
        return new SimpleResponse(HttpStatus.OK, "updated");
    }




    @Override
    public List<Category> getBySearch(String search) {
        return categoryRepo.getBySearch(search);
    }
}

