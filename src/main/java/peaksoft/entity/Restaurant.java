package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.enums.RestType;

import java.math.BigDecimal;
import java.util.List;
@Entity
@Table(name = "restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private RestType restType;
    private Long numberOfEmployees;
    @OneToMany(mappedBy = "restaurant")
    private List<User> user;
    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItem;




}
