package peaksoft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "menuItems")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private int price;
    private String description;
    private Boolean isVegetarian;
    @ManyToMany
    @JsonIgnore
    private List<Cheque> cheque;
    @OneToOne
    @JsonIgnore
    private StopList stopList;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Restaurant restaurant;
    @ManyToOne
    @JsonIgnore
    private SubCategory subCategories;


}
