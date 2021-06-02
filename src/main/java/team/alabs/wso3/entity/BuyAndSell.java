package team.alabs.wso3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    private User buyUser;
    @ManyToOne
    private User sellUser;
    @ManyToOne
    private Ads ads;
}
