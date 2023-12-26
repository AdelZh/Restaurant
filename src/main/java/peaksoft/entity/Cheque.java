package peaksoft.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "cheques")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cheque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int priceAverage;
    private ZonedDateTime createAt;
    @ManyToOne
    private User user;
    @ManyToMany(mappedBy = "cheque")
    private List<MenuItem> menuItem;




}
