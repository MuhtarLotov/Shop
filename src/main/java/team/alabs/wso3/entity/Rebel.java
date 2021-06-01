package team.alabs.wso3.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Rebel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String breaking;
    private String details;
    @ManyToOne
    private User user;

}
