package peaksoft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String image;
    private int price;
    private String description;
    private Boolean isVegetarian;
    @ManyToMany
    @JsonIgnore
    private List<Cheque> cheque;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private StopList stopList;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Restaurant restaurant;
    @ManyToOne
    @JsonIgnore
    private SubCategory subCategory;




}
