package team.alabs.wso3.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Proxy {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String service;
    private String url;

    @ManyToMany
    @JoinTable(name = "proxy_roles", inverseJoinColumns = {@JoinColumn(name = "role")})
    private Set<Role> roles;
}
