package peaksoft.repo;

import jdk.dynalink.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.response.SimpleResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {


    Optional<Category> getCategoriesByName(String name);

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Category> getBySearch(String search);

    @Query("DELETE FROM Category c WHERE c.name = :categoryName")
    int deleteCategoriesByName(@Param("categoryName") Category categoryName);
}


