package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.entity.MenuItem;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {

    @Query("select m from MenuItem m where m.name in :name and m.stopList is null ")
    List<MenuItem> getAll(List<String> name);
    @Query("select m from MenuItem m where m.restaurant.name = :name")
    List<MenuItem> getAll3(String name);

    @Query("SELECT m FROM MenuItem m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<MenuItem> getAll2(String search);
    @Query("select m from MenuItem m order by m.price asc")
    List<MenuItem> sortByPriceAsc();

    @Query("select m from MenuItem m order by m.price desc ")
    List<MenuItem> sortByPriceDesc();


    @Query("select m from MenuItem m where m.isVegetarian = :isVegetarian")
    List<MenuItem> filter(@Param("isVegetarian") boolean isVegetarian);

    @Query("select m from MenuItem m where m.name = :name")
    Optional<MenuItem> getMenuItemsByName(String name);

}
