package team.alabs.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.alabs.shop.enums.Status;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class BuyAndSell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer count;
    private Double price;
    private boolean isActive = true;
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne
    private User buyUser;
    @ManyToOne
    private User sellUser;
    @ManyToOne
    private Ads ads;

}
