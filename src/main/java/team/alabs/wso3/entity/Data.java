package team.alabs.wso3.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@lombok.Data
public class Data {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String service;
    @ManyToOne
    private DatabaseConfiguration databaseConfiguration;
    @ManyToMany
    @JoinTable(name = "data_roles", inverseJoinColumns = {@JoinColumn(name = "role")})
    private Set<Role> roles;
}
