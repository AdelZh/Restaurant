package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> getRestaurantByName(String name);
    Optional<Restaurant> getRestaurantById(Long id);

    @Query("select count(c) from Cheque c join c.menuItem m join m.cheque where m.restaurant.name = :name")
    Long count(String name);

}
