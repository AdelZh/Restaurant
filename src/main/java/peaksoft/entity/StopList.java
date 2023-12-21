package peaksoft.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "stopLists")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StopList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;
    private LocalDate date;
    @OneToOne
    private MenuItem menuItem;
}
