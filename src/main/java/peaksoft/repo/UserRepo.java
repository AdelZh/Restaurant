package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {


    Optional<User> getUserByEmail(String email);

    @Query("select u from User u where u.firstName = :name")
    Optional<User> getUserByFirstName(String name);
    boolean existsByEmail(String email);

    @Query("SELECT t FROM User t WHERE t.email IN :email and t.restaurant.id is null")
    List<User> findByUsername(@Param("email") List<String> email);

    @Query("select count(u.id) from User u join u.restaurant r where r.id = :restaurantId")
    Long countUserByRestaurant(@Param("restaurantId") Long restaurantId);


}
