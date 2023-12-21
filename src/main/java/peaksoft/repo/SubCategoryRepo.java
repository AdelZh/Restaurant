package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entity.MenuItem;
import peaksoft.entity.SubCategory;
import peaksoft.request.CategoryRequest;

import java.util.List;
import java.util.Optional;


public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {


    @Query("select s from SubCategory s join s.category c where c.name = :categoryName order by s.name asc")
    List<SubCategory> getSubCategoriesByCategoryName(String categoryName);


    @Query("select s from SubCategory s join s.category c group by c.name, s.id, s.name")
    List<SubCategory> getAll();

    @Query("SELECT s FROM SubCategory s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<SubCategory> getBySearch(String search);


    @Query("select s from SubCategory s join s.category c where c.name = :name")
    List<SubCategory> getAll2(String name);

    Optional<SubCategory> getSubCategoriesByName(String name);


}

