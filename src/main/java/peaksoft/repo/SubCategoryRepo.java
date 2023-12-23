package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entity.SubCategory;

import java.util.List;
import java.util.Optional;


public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {


    @Query("select s from SubCategory s join s.category c where c.name = :categoryName order by s.name asc")
    List<SubCategory> getSubCategoriesByCategoryName(String categoryName);


    @Query("select s from SubCategory s join s.category c where LOWER(c.name) like LOWER(CONCAT('%', :categoryName, '%'))")
    List<SubCategory> getAll(String categoryName);

    @Query("SELECT s FROM SubCategory s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<SubCategory> getBySearch(String search);

    Optional<SubCategory> getSubCategoriesByName(String name);

    List<SubCategory> getSubCategoriesById(Long id);


}

